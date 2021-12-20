<template>
  <div>
    <DataTable :headers="stammdatenHeader" :product="product" :title="'Produktstammdaten'"></DataTable>
    <DataTable :headers="ilmdHeader" :product="ilmd" :title="'ILMD'"></DataTable>
    <DataTable :headers="eventDataHeader" :product="eventData" :title="'Eventdaten'"></DataTable>
    <Maps :events="events"></Maps>
  </div>
</template>

<script>
import Maps from "@/components/Maps";
import DataTable from '@/components/DataTable'
export default {
  name: "Body.vue",
  props: ['events'],
  components: {
    Maps,
    DataTable
  },
  data () {
    return {
      stammdatenHeader: [
        { text: 'History', value: 'his' },
        { text: 'Produktbezeichnung', value: 'prod' },
        { text: 'Zieltemperatur', value: 'ztemp' },
        { text: 'P1', value: 'p1' },
        { text: 'P2', value: 'p2' },
        { text: 'P3', value: 'p3' },
        { text: 'P4', value: 'p4' },
        { text: 'P5', value: 'p5' },
        { text: 'P6', value: 'p6' },
        { text: 'P7', value: 'p7' },
        { text: 'P8', value: 'p8' },
        { text: 'P9', value: 'p9' },
        { text: 'P10', value: 'p10' },
        { text: 'Erwartete Temperatur', value: 'etemp' },
      ],
      ilmdHeader: [
        { text: 'History', value: 'his' },
        { text: 'Best before date', value: 'mhd' },
        { text: 'lotNumber', value: 'lot' },
        { text: 'BactCount', value: 'bc' },
        { text: 'Aufladewert TTI', value: 'tti' },
        { text: 'Verpackungsdatum', value: 'vdatum' },
      ],
      eventDataHeader: [
        { text: 'History', value: 'his' },
        { text: 'RemainingLive', value: 'rlive' },
        { text: 'Rot', value: 'rot' },
        { text: 'Grün', value: 'gruen' },
        { text: 'Blau', value: 'blau' },
        { text: 'Zeitstempel', value: 'zstempel' },
        { text: 'Laenge', value: 'laenge' },
        { text: 'Breit', value: 'breite' },
      ],
    }
  },
  computed: {
    product: function () {
      let productData = [];
      //this.events.events.reverse();
      for (var key of Object.keys(this.events.events)) {
        if(this.events.events[key].action === "ADD"  && this.events.events[key].bizStep === "urn:epcglobal:cbv:bizstep:commissioning" && this.events.events[key].disposition === "urn:epcglobal:cbv:disp:active") {
          productData.push({
            his: "Produktion",
            prod: this.events.events[key].extension.ilmd.any[5].value == null ? "" : this.events.events[key].extension.ilmd.any[5].value,
            ztemp: this.events.events[key].extension.ilmd.any[6].value,
            p1: this.events.events[key].extension.ilmd.any[7].value,
            p2: this.events.events[key].extension.ilmd.any[8].value,
            p3: this.events.events[key].extension.ilmd.any[9].value,
            p4: this.events.events[key].extension.ilmd.any[10].value,
            p5: this.events.events[key].extension.ilmd.any[11].value,
            p6: this.events.events[key].extension.ilmd.any[12].value,
            p7: this.events.events[key].extension.ilmd.any[13].value,
            p8: this.events.events[key].extension.ilmd.any[14].value,
            p9: this.events.events[key].extension.ilmd.any[15].value,
            p10: this.events.events[key].extension.ilmd.any[16].value,
            etemp: this.events.events[key].extension.ilmd.any[17].value
          })
        }
      }
      return productData;
    },
    ilmd: function() {
      let productData = [];
      for (var key of Object.keys(this.events.events)) {
        if(this.events.events[key].action === "ADD" && this.events.events[key].bizStep === "urn:epcglobal:cbv:bizstep:commissioning" && this.events.events[key].disposition === "urn:epcglobal:cbv:disp:active") {
          productData.push({
            his: "Produktion",
            mhd: this.events.events[key].extension.ilmd.any[0].value,
            lot: this.events.events[key].extension.ilmd.any[1].value,
            bc: this.events.events[key].extension.ilmd.any[2].value,
            tti: this.events.events[key].extension.ilmd.any[3].value,
            vdatum: this.events.events[key].eventTime,
          })
        }
      }
      return productData;
    },
    eventData: function() {
      let productData = [];
      for (var key of Object.keys(this.events.events)) {
        if(this.events.events[key].bizStep !== "urn:epcglobal:cbv:bizstep:commissioning" && this.events.events[key].disposition !== "urn:epcglobal:cbv:disp:active") {
          productData.push({
            his: this.eventCategory(key),
            rlive: this.events.events[key].any[0].value == null ? 0 : this.events.events[key].any[0].value,
            rot: this.events.events[key].any[1].value == null ? 0 : this.events.events[key].any[1].value,
            gruen: this.events.events[key].any[2].value == null ? 0 : this.events.events[key].any[2].value,
            blau: this.events.events[key].any[3].value == null ? 0 : this.events.events[key].any[3].value,
            zstempel: this.events.events[key].eventTime,
            laenge: this.convertGeoLocation(this.events.events[key].readPoint.id).lat,
            breite: this.convertGeoLocation(this.events.events[key].readPoint.id).long,
          })
        }
      }
      return productData;
    },
  },
  methods: {
    convertGeoLocation(geoLocation) {
      const fields = geoLocation.split(":");
      let latLong = fields[1];
      const fields2 = latLong.split(",");
      return {"lat": fields2[0], "long": fields2[1]};
    },
    eventCategory(key) {
      if(this.events.events[key].action === "OBSERVE") {
        if(this.events.events[key].bizStep === "urn:epcglobal:cbv:bizstep:storing" && this.events.events[key].disposition === "urn:epcglobal:cbv:disp:in_progress") {
          return this.eventType('2');
        } else if(this.events.events[key].bizStep === "urn:epcglobal:cbv:bizstep:shipping" && this.events.events[key].disposition === "urn:epcglobal:cbv:disp:in_transit") {
          return this.eventType('3');
        } else if(this.events.events[key].bizStep === "urn:epcglobal:cbv:bizstep:receiving" && this.events.events[key].disposition === "urn:epcglobal:cbv:disp:in_progress") {
          return this.eventType('4');
        } else if(this.events.events[key].bizStep === "urn:epcglobal:cbv:bizstep:storing" && this.events.events[key].disposition === "urn:epcglobal:cbv:disp:sellable_accessible") {
          return this.eventType('5');
        } else if(this.events.events[key].bizStep === "urn:epcglobal:cbv:bizstep:storing" && this.events.events[key].disposition === "urn:epcglobal:cbv:disp:sellable_not_accessible") {
          return this.eventType('6');
        } else if(this.events.events[key].bizStep === "urn:epcglobal:cbv:bizstep:storing" && this.events.events[key].disposition === "urn:epcglobal:cbv:disp:non_sellable_expired") {
          return this.eventType('7');
        } else if(this.events.events[key].bizStep === "urn:epcglobal:cbv:bizstep:inspecting" && this.events.events[key].disposition === "urn:epcglobal:cbv:disp:in_progress") {
          return this.eventType('8');
        } else if(this.events.events[key].bizStep === "urn:epcglobal:cbv:bizstep:inspecting" && this.events.events[key].disposition === "urn:epcglobal:cbv:disp:expired") {
          return this.eventType('10');
        }
      } else if(this.events.events[key].action === "ADD") {
        if(this.events.events[key].bizStep === "urn:epcglobal:cbv:bizstep:retail_selling" && this.events.events[key].disposition === "urn:epcglobal:cbv:disp:retail_sold") {
          return this.eventType('9');
        }
      } else if(this.events.events[key].action === "DELETE") {
        if(this.events.events[key].bizStep === "urn:epcglobal:cbv:bizstep:decommissioning" && this.events.events[key].disposition === "urn:epcglobal:cbv:disp:inactive") {
          return this.eventType('11');
        }
      }
    },
    eventType(row) {
      switch (row) {
        case '1':
          return "Produktion";
        case '2':
          return "Logistik: Einlagern";
        case '3':
          return "Logistik: Warenausgang";
        case '4':
          return "Logistik: Wareneingang ";
        case '5':
          return "Handel: Einlagern im Backroom";
        case '6':
          return "Handel: In den Verkauf bringen";
        case '7':
          return "Handel: Ware ist abgelaufen ";
        case '8':
          return "Handel: Prüfung der Restlaufzeit"
        case '9':
          return "Handel: Verkauf an der Kasse"
        case '10':
          return "Konsument: Restlaufzeit bestimmen"
        case '11':
          return "Konsument:  Ware verbrauchen"
      }
    }
  }
}
</script>

<style scoped>

</style>
