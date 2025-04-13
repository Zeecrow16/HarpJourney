package com.example.harpjourneyapp.data

import com.example.harpjourneyapp.enum.SkillLevel

class QuestionsRepository {

        private val questions = listOf(

            // BEGINNER
            HarpQuestions("What is the red string on the harp?", listOf("C", "G"), "C", SkillLevel.BEGINNER),
            HarpQuestions("Which hand usually plays the melody?", listOf("Left hand", "Right hand"), "Right hand", SkillLevel.BEGINNER),
            HarpQuestions("How many strings does a lever harp typically have?", listOf("34", "21"), "34", SkillLevel.BEGINNER),
            HarpQuestions("What is the white string usually tuned to?", listOf("F", "E"), "F", SkillLevel.BEGINNER),
            HarpQuestions("What is a common tuning for a lever harp?", listOf("C major", "G major"), "C major", SkillLevel.BEGINNER),
            HarpQuestions("What should your fingers do after plucking?", listOf("Relax", "Close into your palm"), "Close into your palm", SkillLevel.BEGINNER),
            HarpQuestions("Which number corresponds to your thumb in harp finger numbering?", listOf("1", "5"), "1", SkillLevel.BEGINNER),
            HarpQuestions("What’s the best posture when playing?", listOf("Sit up straight", "Lean into the harp"), "Sit up straight", SkillLevel.BEGINNER),
            HarpQuestions("How often should you tune your harp?", listOf("Once a week", "Before every session"), "Before every session", SkillLevel.BEGINNER),
            HarpQuestions("What’s a good first song to learn?", listOf("Twinkle Twinkle", "Fur Elise"), "Twinkle Twinkle", SkillLevel.BEGINNER),
            HarpQuestions("What is used to change pitch on a lever harp?", listOf("Pedals", "Levers"), "Levers", SkillLevel.BEGINNER),
            HarpQuestions("Should your wrists be high or low while playing?", listOf("High", "Low"), "High", SkillLevel.BEGINNER),

            // INTERMEDIATE
            HarpQuestions("Which intervals sound most harmonious?", listOf("Thirds", "Seconds"), "Thirds", SkillLevel.INTERMEDIATE),
            HarpQuestions("What’s a glissando?", listOf("A quick scale run", "A chord"), "A quick scale run", SkillLevel.INTERMEDIATE),
            HarpQuestions("What’s a rolled chord?", listOf("Notes played quickly one after another", "All at once"), "Notes played quickly one after another", SkillLevel.INTERMEDIATE),
            HarpQuestions("Which scale has no sharps or flats?", listOf("C Major", "G Major"), "C Major", SkillLevel.INTERMEDIATE),
            HarpQuestions("How do you play in a different key on lever harp?", listOf("Use levers", "Use pedals"), "Use levers", SkillLevel.INTERMEDIATE),
            HarpQuestions("What is damping?", listOf("Stopping strings from ringing", "Plucking harder"), "Stopping strings from ringing", SkillLevel.INTERMEDIATE),
            HarpQuestions("What is the interval between C and G?", listOf("Fifth", "Fourth"), "Fifth", SkillLevel.INTERMEDIATE),
            HarpQuestions("Which finger is not used in harp playing?", listOf("Pinky (5)", "Index (2)"), "Pinky (5)", SkillLevel.INTERMEDIATE),
            HarpQuestions("What is a good finger exercise?", listOf("Scales", "Tapping"), "Scales", SkillLevel.INTERMEDIATE),
            HarpQuestions("What dynamic marking means 'soft'?", listOf("p", "f"), "p", SkillLevel.INTERMEDIATE),
            HarpQuestions("What’s the benefit of overlapping brackets?", listOf("Smoother transitions", "Louder sound"), "Smoother transitions", SkillLevel.INTERMEDIATE),
            HarpQuestions("What's a good warm-up routine?", listOf("Finger rolls", "Sight reading"), "Finger rolls", SkillLevel.INTERMEDIATE),

            // ADVANCED
            HarpQuestions("What is a harmonic?", listOf("Touching midpoint while plucking", "Striking with nail"), "Touching midpoint while plucking", SkillLevel.ADVANCED),
            HarpQuestions("What does the pedal on a pedal harp change?", listOf("String pitch", "String tension"), "String pitch", SkillLevel.ADVANCED),
            HarpQuestions("What’s the enharmonic of G#?", listOf("Ab", "A#"), "Ab", SkillLevel.ADVANCED),
            HarpQuestions("What is cross-fingering used for?", listOf("Arpeggios", "Playing scales smoothly"), "Playing scales smoothly", SkillLevel.ADVANCED),
            HarpQuestions("Which clef do you usually read for right hand?", listOf("Treble", "Bass"), "Treble", SkillLevel.ADVANCED),
            HarpQuestions("What does ‘ritardando’ mean?", listOf("Slow down", "Speed up"), "Slow down", SkillLevel.ADVANCED),
            HarpQuestions("How many pedals are on a pedal harp?", listOf("7", "5"), "7", SkillLevel.ADVANCED),
            HarpQuestions("Which composer wrote for harp extensively?", listOf("Debussy", "Beethoven"), "Debussy", SkillLevel.ADVANCED),
            HarpQuestions("What is a glissando often used to express?", listOf("Emotion or emphasis", "Tempo change"), "Emotion or emphasis", SkillLevel.ADVANCED),
            HarpQuestions("What’s an arpeggio?", listOf("Broken chord", "Flat note"), "Broken chord", SkillLevel.ADVANCED),
            HarpQuestions("Which fingers should cross in a cross-over?", listOf("Thumb over 3rd", "4th over 2nd"), "Thumb over 3rd", SkillLevel.ADVANCED),
            HarpQuestions("How do you play in D major on lever harp?", listOf("Raise F and C levers", "Lower F and C levers"), "Raise F and C levers", SkillLevel.ADVANCED),

            // EXPERT
            HarpQuestions("What technique uses overlapping hands?", listOf("Cross-hand technique", "Muting technique"), "Cross-hand technique", SkillLevel.EXPERT),
            HarpQuestions("What is a cadenza?", listOf("Ornamental solo passage", "Tempo marking"), "Ornamental solo passage", SkillLevel.EXPERT),
            HarpQuestions("Which composer famously wrote 'Danses sacrée et profane'?", listOf("Debussy", "Mozart"), "Debussy", SkillLevel.EXPERT),
            HarpQuestions("What is the lever configuration for E-flat major?", listOf("All Bs, Es, and As down", "All Bs, Es, and As up"), "All Bs, Es, and As down", SkillLevel.EXPERT),
            HarpQuestions("How do enharmonics affect lever harp tuning?", listOf("Limit available keys", "Have no effect"), "Limit available keys", SkillLevel.EXPERT),
            HarpQuestions("What’s a ‘bisbigliando’?", listOf("Whispering effect with both hands", "Staccato pattern"), "Whispering effect with both hands", SkillLevel.EXPERT),
            HarpQuestions("What is a common time signature in advanced harp repertoire?", listOf("6/8", "3/2"), "6/8", SkillLevel.EXPERT),
            HarpQuestions("What does ‘a tempo’ mean?", listOf("Return to original tempo", "Play slower"), "Return to original tempo", SkillLevel.EXPERT),
            HarpQuestions("How do you achieve dynamic contrast on harp?", listOf("Pluck with varying force", "Move closer to soundboard"), "Pluck with varying force", SkillLevel.EXPERT),
            HarpQuestions("What is a double harmonic?", listOf("Two harmonics at once", "An octave plucked with thumb"), "Two harmonics at once", SkillLevel.EXPERT),
            HarpQuestions("Which of these is NOT an extended technique?", listOf("Vibrato", "Buzzing"), "Vibrato", SkillLevel.EXPERT),
            HarpQuestions("In orchestral tuning, what note is the harp tuned to?", listOf("C", "A"), "C", SkillLevel.EXPERT),
        )

        fun getRandomQuestions(level: SkillLevel, count: Int = 3): List<HarpQuestions> {
            return questions.filter { it.level == level }.shuffled().take(count)
        }

}