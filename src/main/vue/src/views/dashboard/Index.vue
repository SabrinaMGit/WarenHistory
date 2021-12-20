<template>
  <base-section
    id="dashboard"
    fill-height
  >
    <v-card>
      <v-card-title>
        Alle Event Daten
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="product"
        :search="search"
      ></v-data-table>
    </v-card>

    <div  style="height: 500px; width: 100%">
      <l-map
        v-if="showMap"
        :zoom="zoom"
        :center="center"
        :options="mapOptions"
        style="height: 80%"
        @update:center="centerUpdate"
        @update:zoom="zoomUpdate"
      >
        <l-tile-layer
          :url="url"
          :attribution="attribution"
        />
        <l-marker v-for="(marker, index) in markers" :key="index" :lat-lng="marker.loc">
          <l-popup :options="{ permanent: true, interactive: true }">
            {{ marker.address }}
          </l-popup>
        </l-marker>
        <l-polyline
          :lat-lngs="polyline.latlngs"
          :color="polyline.color"
        />
      </l-map>
    </div>
  </base-section>

</template>

<script>
//import AllBody from "@/components/AllBody";
import { latLng } from "leaflet";
import { LMap, LTileLayer, LTooltip, LPolyline, LPopup } from "vue2-leaflet";
export default {
  name: 'Dashboard',
  data: function (){
    return {
      events: [],
      search: '',
      zoom: 13,
      center: latLng(49.31503, 12.15252),
      url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution:
        '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      withPopup: latLng(51.15636, 6.74023),
      currentZoom: 11.5,
      currentCenter: latLng(49.31503,	12.15252),
      showParagraph: false,
      mapOptions: {
        zoomSnap: 0.5
      },
      showMap: true,
      markerLatLng: [51.15636, 6.74023],
      markers: [],
      polyline: {
        latlngs: [],
        color: "blue"
      },
      glnsData: [],
      headers: [
        { text: 'History', value: 'his' },
        { text: 'SGTIN', value: 'sgtin'},
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
        { text: 'Best before date', value: 'mhd' },
        { text: 'lotNumber', value: 'lot' },
        { text: 'BactCount', value: 'bc' },
        { text: 'Aufladewert TTI', value: 'tti' },
        { text: 'Verpackungsdatum', value: 'vdatum' },
        { text: 'RemainingLive', value: 'rlive' },
        { text: 'Rot', value: 'rot' },
        { text: 'Grün', value: 'gruen' },
        { text: 'Blau', value: 'blau' },
        { text: 'Laenge', value: 'laenge' },
        { text: 'Breit', value: 'breite' },
      ],
    }
  },
  components: {
    //AllBody
    LMap,
    LTileLayer,
    LTooltip,
    LPolyline,
    LPopup
  },
  computed: {
    product: function () {
      let productData = [];
      //this.events.events.reverse();
      for (var key of Object.keys(this.events)) {
        console.log("Data: " + this.events[key])
         productData.push({
          his: this.eventCategory(key),
          sgtin: this.events[key].epcList.epc[0].value,
          vdatum: this.events[key].eventTime,
          laenge: this.convertGeoLocation(this.events[key].readPoint.id).lat,
          breite: this.convertGeoLocation(this.events[key].readPoint.id).long,
        })
      }

        /*       if( this.events[key].extension == null ) {
                  productData.push({
                    prod: this.events[key].extension.ilmd.any[5].value == null ? "" : this.events[key].extension.ilmd.any[5].value,
                    ztemp: this.events[key].extension.ilmd.any[6].value,
                    p1: this.events[key].extension.ilmd.any[7].value,
                    p2: this.events[key].extension.ilmd.any[8].value,
                    p3: this.events[key].extension.ilmd.any[9].value,
                    p4: this.events[key].extension.ilmd.any[10].value,
                    p5: this.events[key].extension.ilmd.any[11].value,
                    p6: this.events[key].extension.ilmd.any[12].value,
                    p7: this.events[key].extension.ilmd.any[13].value,
                    p8: this.events[key].extension.ilmd.any[14].value,
                    p9: this.events[key].extension.ilmd.any[15].value,
                    p10: this.events[key].extension.ilmd.any[16].value,
                    etemp: this.events[key].extension.ilmd.any[17].value,
                    mhd: this.events[key].extension.ilmd.any[0].value,
                    lot: this.events[key].extension.ilmd.any[1].value,
                    bc: this.events[key].extension.ilmd.any[2].value,
                    tti: this.events[key].extension.ilmd.any[3].value,
                  })
                }
        if (this.events[key].any.length > 0) {
          productData.push({
            rlive: this.events[key].any[0].value,
            rot: this.events[key].any[1].value,
            gruen: this.events[key].any[2].value,
            blau: this.events[key].any[3].value
          })
        }
      }*/




      return productData;
    },
  },
  mounted() {
    this.accessAPI()
    this.zoom = 5.5
  },
  methods: {
    accessAPI: function () {
      this.events = {}
      this.axios({
        url: "/admin/api/access/all",
        method: "GET"
      }).then(response => {
        console.log("events: " + response.data[2].epcList.epc[0].value)
        this.events = response.data;
        this.fillAllMarkers();
      })
        .catch(e => {
          if (e != null) {
            this.events = {}
            this.flashMessage.error({
              title: 'ERROR',
              message: 'Keine Daten vorhanden!'
            });
          }
        });
    },
    eventCategory(key) {
      if (this.events[key].action === "ADD" && this.events[key].bizStep === "urn:epcglobal:cbv:bizstep:commissioning" && this.events[key].disposition === "urn:epcglobal:cbv:disp:active") {
        return this.eventType('1');
      } else if (this.events[key].action === "OBSERVE") {
        if (this.events[key].bizStep === "urn:epcglobal:cbv:bizstep:storing" && this.events[key].disposition === "urn:epcglobal:cbv:disp:in_progress") {
          return this.eventType('2');
        } else if (this.events[key].bizStep === "urn:epcglobal:cbv:bizstep:shipping" && this.events[key].disposition === "urn:epcglobal:cbv:disp:in_transit") {
          return this.eventType('3');
        } else if (this.events[key].bizStep === "urn:epcglobal:cbv:bizstep:receiving" && this.events[key].disposition === "urn:epcglobal:cbv:disp:in_progress") {
          return this.eventType('4');
        } else if (this.events[key].bizStep === "urn:epcglobal:cbv:bizstep:storing" && this.events[key].disposition === "urn:epcglobal:cbv:disp:sellable_accessible") {
          return this.eventType('5');
        } else if (this.events[key].bizStep === "urn:epcglobal:cbv:bizstep:storing" && this.events[key].disposition === "urn:epcglobal:cbv:disp:sellable_not_accessible") {
          return this.eventType('6');
        } else if (this.events[key].bizStep === "urn:epcglobal:cbv:bizstep:storing" && this.events[key].disposition === "urn:epcglobal:cbv:disp:non_sellable_expired") {
          return this.eventType('7');
        } else if (this.events[key].bizStep === "urn:epcglobal:cbv:bizstep:inspecting" && this.events[key].disposition === "urn:epcglobal:cbv:disp:in_progress") {
          return this.eventType('8');
        } else if (this.events[key].bizStep === "urn:epcglobal:cbv:bizstep:inspecting" && this.events[key].disposition === "urn:epcglobal:cbv:disp:expired") {
          return this.eventType('10');
        }
      } else if (this.events[key].action === "ADD") {
        if (this.events[key].bizStep === "urn:epcglobal:cbv:bizstep:retail_selling" && this.events[key].disposition === "urn:epcglobal:cbv:disp:retail_sold") {
          return this.eventType('9');
        }
      } else if (this.events[key].action === "DELETE") {
        if (this.events[key].bizStep === "urn:epcglobal:cbv:bizstep:decommissioning" && this.events[key].disposition === "urn:epcglobal:cbv:disp:inactive") {
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
    },
    fillAllMarkers() {
      let locations = [];
      for (let key of Object.keys(this.events)) {
        locations.push({loc: [this.convertGeoLocation(this.events[key].readPoint.id).lat, this.convertGeoLocation(this.events[key].readPoint.id).long]});
      }
        console.log("loc: "+locations)


     /* this.markers.push({
        loc: [this.convertGeoLocation(this.events[key].readPoint.id).lat, this.convertGeoLocation(this.events[key].readPoint.id).long],
        address: "GLN: 4016623000002\n" +
          "WOLF WURSTSPEZIALITÄTEN GMBH\n" +
          "Am Lindenhof 40\n" +
          "D-04626 Schmölln\n" +
          "Tel. 034491 31-0\n" +
          "Fax 034491 31-111\n"
      });
      this.polyline.latlngs.push([this.convertGeoLocation(this.events[key].readPoint.id).lat, this.convertGeoLocation(this.events[key].readPoint.id).long]);
      this.centerUpdate(latLng(this.convertGeoLocation(this.events[key].readPoint.id).lat, this.convertGeoLocation(this.events[key].readPoint.id).long));*/
    },
    zoomUpdate(zoom) {
      this.currentZoom = zoom;
    },
    centerUpdate(center) {
      this.currentCenter = center;
    },
    showLongText() {
      this.showParagraph = !this.showParagraph;
    },
    innerClick() {
      alert("Click!");
    },
    convertGeoLocation(geoLocation) {
      const fields = geoLocation.split(":");
      let latLong = fields[1];
      const fields2 = latLong.split(",");
      return {"lat": fields2[0], "long": fields2[1]};
    },
  }
}
</script>

