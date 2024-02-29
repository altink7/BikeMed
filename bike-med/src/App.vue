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
        plattenReparatur: ['Sind Reifen intakt?'],
        ventil: ['Ist das Ventil intakt?'],
        bremsen: ['Funktionieren die Bremsen ordnungsgemäß?'],
        schaltung: ['Schaltet die Gangschaltung korrekt?'],
        beleuchtungVorne: ['Funktioniert das  Vorderlicht?'],
        beleuchtungHinten: ['Funktioniert das Rücklicht?'],
        reflector: ['Sind die Reflektoren intakt?'],
        federung: ['Funktioniert die Federung?'],
        rahmen: ['Es gibt keine Risse oder Beschädigungen am Rahmen?'],
        gabel: ['Es gibt keine Risse oder Beschädigungen an der Gabel?'],
        kettenantrieb: ['Funktioniert die Kette ordnungsgemäß?'],
        elektrischeKomponenten: ['Es gibt keine Probleme mit der elektrischen Steuerung?'],
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
        const response = await axios.post('https://bike-med-api-1709188487163.azurewebsites.net/api/diagnose', diagnose);
        alert('Identifikationsnummer: ' + response.data);
        console.log('Diagnose abgeschickt', response.data);

        const pdfResponse = await axios.get('https://bike-med-office-1709207940776.azurewebsites.net/api/pdf/' + response.data, { responseType: 'blob' });
        const file = new Blob([pdfResponse.data], { type: 'application/pdf' });
        const fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      } catch (error) {
        console.error('Error submitting diagnosis', error);
      }
    }
  }
};
</script>