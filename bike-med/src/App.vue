<template>
  <div id="app">
    <h1>Bike Diagnose App</h1>
    <form @submit.prevent="submitDiagnosis">
      <div v-for="(questions, category) in diagnosisQuestions" :key="category">
        <h2>{{ category }}</h2>
        <div v-for="(question, index) in questions" :key="index">
          <label>
            <input type="checkbox" v-model="selectedComponents" :value="category + '_' + index">
            {{ question }}
          </label>
        </div>
      </div>
      <div v-if="selectedComponents.includes('sonstigeProbleme_' + (diagnosisQuestions.sonstigeProbleme.length - 1))">
        <label for="customNote">Benutzerdefinierte Anmerkungen:</label>
        <textarea v-model="customNote" id="customNote"></textarea>
      </div>
      <button type="submit">Diagnose abschicken</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      diagnosisQuestions: {
        plattenReparatur: ['Ist ein Reifen platt?'],
        ventil: ['Ist das Ventil intakt?'],
        bremsen: ['Funktionieren die Bremsen ordnungsgemäß?'],
        schaltung: ['Schaltet die Gangschaltung korrekt?'],
        beleuchtungVorne: ['Funktioniert das  Vorderlicht?'],
        beleuchtungHinten: ['Funktioniert das Rücklicht?'],
        reflector: ['Sind die Reflektoren intakt?'],
        federung: ['Gibt es Probleme mit der Federung?'],
        rahmen: ['Gibt es Risse oder Beschädigungen am Rahmen?'],
        gabel: ['Gibt es Risse oder Beschädigungen an der Gabel?'],
        kettenantrieb: ['Funktioniert die Kette ordnungsgemäß?'],
        elektrischeKomponenten: ['Gibt es Probleme mit der elektrischen Steuerung?'],
        sonstigeProbleme: ['Benutzerdefinierte Anmerkungen']
      },
      selectedComponents: [],
      customNote: ''
    };
  },
  methods: {
    async submitDiagnosis() {
      try {
        let diagnose = {
          plattenReparatur: this.selectedComponents.includes('plattenReparatur_0'),
          ventil: this.selectedComponents.includes('ventil_0'),
          bremsen: this.selectedComponents.includes('bremsen_0'),
          schaltung: this.selectedComponents.includes('schaltung_0'),
          beleuchtungVorne: this.selectedComponents.includes('beleuchtungVorne_0'),
          beleuchtungHinten: this.selectedComponents.includes('beleuchtungHinten_0'),
          reflector: this.selectedComponents.includes('reflector_0'),
          federung: this.selectedComponents.includes('federung_0'),
          rahmen: this.selectedComponents.includes('rahmen_0'),
          gabel: this.selectedComponents.includes('gabel_0'),
          kettenantrieb: this.selectedComponents.includes('kettenantrieb_0'),
          elektrischeKomponenten: this.selectedComponents.includes('elektrischeKomponenten_0'),
          sonstigeProbleme: this.selectedComponents.includes('sonstigeProbleme_0'),
          customNote: this.customNote
        };

        console.log('Diagnose:', diagnose);
        const response = await axios.post('http://localhost:5001/api/diagnose', diagnose);
        console.log('Diagnose abgeschickt', response.data);
      } catch (error) {
        console.error('Error submitting diagnosis', error);
      }
    }
  }
};
</script>