<template>
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
    </l-map>
  </div>
</template>

<script>
//import L from 'leaflet';
import { latLng } from "leaflet";
import { LMap } from "vue2-leaflet";

export default {
  name: "Example",
  props: [],
  components: {
    LMap,

  },
  data() {
    return {
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
    };
  },
  methods: {
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
    }
  }
};
</script>
