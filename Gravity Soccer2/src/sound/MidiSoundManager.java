package sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import javax.sound.midi.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MidiSoundManager {
    private final List<Integer> notes = new ArrayList<>();
    private final Map<Integer, Sound> noteSounds = new HashMap<>();
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    /**
     * @param midiFilePath   chemin vers le .mid (ex: "midi/theme.mid")
     * @param sampleDir      dossier des notes, ex: "sound/notes/"
     *                        Contient 60.ogg, 61.ogg, … pour chaque pitch.
     */
    public MidiSoundManager(String midiFilePath, String sampleDir) {
        loadMidi(midiFilePath);
        loadSamples(sampleDir);
    }

    /** Parse le MIDI et remplit la liste `notes`. */
    private void loadMidi(String midiFilePath) {
        notes.clear();
        try {
            Sequence seq = MidiSystem.getSequence(new File(Gdx.files.internal(midiFilePath).path()));
            // Map tick -> liste de hauteurs "Note On"
            TreeMap<Long, List<Integer>> tickMap = new TreeMap<>();
            for (Track track : seq.getTracks()) {
                for (int i = 0; i < track.size(); i++) {
                    MidiEvent ev = track.get(i);
                    MidiMessage msg = ev.getMessage();
                    if (msg instanceof ShortMessage sm
                     && sm.getCommand() == ShortMessage.NOTE_ON
                     && sm.getData2() > 0) {
                        tickMap
                          .computeIfAbsent(ev.getTick(), k -> new ArrayList<>())
                          .add(sm.getData1());
                    }
                }
            }
            // Pour chaque tick, on garde la note la plus haute
            for (List<Integer> list : tickMap.values()) {
                notes.add(Collections.max(list));
            }
            Gdx.app.log("MidiSoundMgr", "Loaded " + notes.size() + " notes from MIDI");
        } catch (Exception e) {
            Gdx.app.error("MidiSoundMgr", "Error parsing MIDI", e);
        }
    }

    /** Pré-charge les samples libGDX pour chaque pitch. */
    private void loadSamples(String sampleDir) {
        // Collecte unique des pitches
        Set<Integer> uniquePitches = new HashSet<>(notes);
        for (int pitch : uniquePitches) {
            String path = sampleDir + pitch + ".ogg";  // ou .wav
            FileHandle fh = Gdx.files.internal(path);
            if (!fh.exists()) {
                Gdx.app.error("MidiSoundMgr", "Sample missing: " + path);
                continue;
            }
            Sound s = Gdx.audio.newSound(fh);
            noteSounds.put(pitch, s);
        }
        Gdx.app.log("MidiSoundMgr", "Loaded samples for " + noteSounds.size() + " pitches");
    }

    /** Joue la note suivante dans la liste (appelé à chaque collision). */
    public void playNextNote() {
        if (notes.isEmpty()) return;
        int idx = currentIndex.getAndUpdate(i -> (i + 1) % notes.size());
        int pitch = notes.get(idx);
        Sound s = noteSounds.get(pitch);
        if (s != null) {
            s.play(1.0f);  // volume 0–1
        } else {
            Gdx.app.log("MidiSoundMgr", "No sample for pitch " + pitch);
        }
    }

    /** Dispose tous les `Sound`. */
    public void dispose() {
        for (Sound s : noteSounds.values()) s.dispose();
        noteSounds.clear();
    }
}
