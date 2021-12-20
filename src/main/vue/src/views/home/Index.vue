<template>
  <div>
    <v-container class="lighten-5">
      <v-row no-gutters
          v-for="n in 1"
          :key="n"
          cols="12"
          sm="2"
        >
            <v-text-field
              v-model="gtin"
              label="GTIN*"
              @keyup.enter="submit(gtin, ser, digitallink)"
            ></v-text-field>
        <v-spacer></v-spacer>
            <v-text-field
              v-model="ser"
              label="Serilisierungsnummer*"
              @keyup.enter="submit(gtin, ser, digitallink)"
            ></v-text-field>
        <v-spacer></v-spacer>
        <v-text-field
          v-model="digitallink"
          label="Digitallink"
          @keyup.enter="submit(gtin, ser, digitallink)"
        ></v-text-field>
        <v-spacer></v-spacer>
            <v-btn
              class="mr-4"
              @click="submit(gtin, ser, digitallink)"
            >
              send
            </v-btn>
            <v-btn @click="clear">
              clear
            </v-btn>
      </v-row>
    </v-container>
    <Body v-if="events != null && Object.keys(events).length > 0"
    :events="events"></Body>
  </div>
</template>

<script>
import Body from "@/components/Body";

export default {
  name: "index",
  data: function (){
    return {
    gtin: this.$route.params.gtin,
    ser: this.$route.params.ser,
    digitallink: '',
    aSer: '',
    eSer: '',
    events: '',
    }
  },
  components: {
    Body
  },
  methods: {
    submit (gtin, ser, digitallink) {
      this.resetPath()
      this.splitDigitallink(gtin, ser, digitallink)
    },
    clear () {
      this.events = null
      this.produktbezeichnung = ''
      this.produktionscharge = ''
      this.mhd = ''
      this.aSer = ''
      this.eSer = ''
      this.resetPath()
    },
    resetPath () {
      let path = "/";
      if (this.$route.path !== path) {
        this.$router.push(path)
      }
    },
    accessAPI: function (gtin, ser, digitallink) {
      if (this.validateInputs(gtin, ser, digitallink)) {
        this.events = {}
        let queryUrl = "/gtin/" + gtin + "/ser/" + ser;
        this.axios({
          url: "api/access/digitalLink",
          params: {sgtin: "https://id.intelli-pack.de/01/"+gtin+"/21/"+ser},
          method: "GET"
        })
          .then(response => {
            this.events = response.data;
            //let url = "/01/" + gtin + "/21/" + ser;
           /* if (this.$route.path !== queryUrl) {
              this.$router.push(queryUrl);
            }*/
          })
          .catch(e => {
            if (e != null) {
              this.events = {}
              this.flashMessage.error({
                title: 'ERROR',
                message: 'Ein Problem ist aufgetreten. Überprüfe die Sgtin.'
              });
            }
          });
      } else {
        this.flashMessage.setStrategy('single');
        this.flashMessage.info({
          title: 'INFO',
          message: 'GTIN und Serialisierungsnummer muss ausgefüllt sein',
          icon: '',
          clickable: true,
          time: 10000,
          padding: 500
        });
      }
    },
    validateInput: function() {
      return this.gtin && this.ser;
    },
    splitDigitallink(gtin, ser, digitallink){
        //var input = 'https://id.intelli-pack.de/01/0606949031244/21/1234';
        if(this.digitallink !== ''){
          const fields = digitallink.split('/');
          let gtinF = fields[4];
          let serF = fields[6];
          this.accessAPI(gtinF, serF, digitallink);
        } else {
          this.accessAPI(gtin, ser, digitallink);
        }
    },
    validateInputs: function(gtin, ser) {
      return gtin && ser;
    }
  },
  mounted() {
    this.submit(this.gtin, this.ser, this.digitallink);
  }
}
</script>

