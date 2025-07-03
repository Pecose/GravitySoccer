package sound;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class SoundManager {
    private final SoundFontManager sfManager;
    private final List<Integer> notes = new ArrayList<>();
    private int currentIndex = 0;
    private final int noteDurationMs = 300;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public SoundManager(String sf2FilePath) {
        try {
            sfManager = new SoundFontManager(sf2FilePath);
            sfManager.setProgram(0, 0);
        } catch (Exception e) {
            throw new RuntimeException("Impossible de charger le SoundFont: " + sf2FilePath, e);
        }
    }

    /**
     * Parse un .mid et extrait la note la plus haute par tick (heuristique mélodique).
     * Ainsi les accords sont réduits à leur note principale.
     * @param midiFilePath chemin vers le .mid (relatif ou absolu)
     */
    public void loadMidi(String midiFilePath) {
        notes.clear();
        currentIndex = 0;
        try {
            Sequence seq = MidiSystem.getSequence(new File(midiFilePath));
            // Map tick -> liste de pitches ON à ce tick
            Map<Long, List<Integer>> tickToPitches = new TreeMap<>();
            for (Track track : seq.getTracks()) {
                for (int i = 0; i < track.size(); i++) {
                    MidiEvent ev = track.get(i);
                    MidiMessage msg = ev.getMessage();
                    if (msg instanceof ShortMessage sm
                      && sm.getCommand() == ShortMessage.NOTE_ON
                      && sm.getData2() > 0) {
                        long tick = ev.getTick();
                        tickToPitches
                            .computeIfAbsent(tick, k -> new ArrayList<>())
                            .add(sm.getData1());
                    }
                }
            }
            // Pour chaque tick, prends la note la plus haute
            for (List<Integer> pList : tickToPitches.values()) {
                int topPitch = Collections.max(pList);
                notes.add(topPitch);
            }
        } catch (Exception e) {
            System.err.println("Erreur parsing MIDI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * À appeler à chaque collision :
     * - Stoppe l'éventuelle note en cours,
     * - Joue la note courante,
     * - Programme son arrêt et prépare la suivante.
     */
    public synchronized void playNextNote() {
        if (notes.isEmpty()) return;
        sfManager.allNotesOff();

        int pitch = notes.get(currentIndex);
        sfManager.playNote(pitch, 100);
        currentIndex = (currentIndex + 1) % notes.size();

        scheduler.schedule(() -> sfManager.allNotesOff(), noteDurationMs, TimeUnit.MILLISECONDS);
    }

    /**
     * Libère les ressources.
     */
    public void dispose() {
        scheduler.shutdownNow();
        sfManager.close();
    }
}
