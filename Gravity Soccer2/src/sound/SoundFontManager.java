package sound;

import java.io.File;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class SoundFontManager {
    private final Synthesizer synth;
    private final MidiChannel channel;

    /**
     * Charge un SoundFont et prépare le canal MIDI 0.
     * @param sf2FilePath chemin vers le fichier .sf2
     * @throws Exception si l'initialisation échoue
     */
    public SoundFontManager(String sf2FilePath) throws Exception {
        synth = MidiSystem.getSynthesizer();
        synth.open();

        Soundbank sb = MidiSystem.getSoundbank(new File(sf2FilePath));
        synth.loadAllInstruments(sb);
        channel = synth.getChannels()[0];
    }

    /**
     * Change de preset/instrument sur le canal.
     */
    public void setProgram(int bank, int program) {
        channel.programChange(bank, program);
    }

    /**
     * Joue une note MIDI.
     */
    public void playNote(int pitch, int velocity) {
        channel.noteOn(pitch, velocity);
    }

    /**
     * Coupe toutes les notes en cours sur tous les canaux.
     */
    public void allNotesOff() {
        for (MidiChannel ch : synth.getChannels()) {
            ch.allNotesOff();
        }
    }

    /**
     * Ferme le synthétiseur.
     */
    public void close() {
        synth.close();
    }
}