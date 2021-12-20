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
      <l-tile-layer
        :url="url"
        :attribution="attribution"
      />
      <l-marker v-for="(marker, index) in markers" :key="index" :lat-lng="marker.loc">
        <l-tooltip :options="{ permanent: true, interactive: true }">
            {{ marker.address }}
        </l-tooltip>
      </l-marker>
      <l-polyline
        :lat-lngs="polyline.latlngs"
        :color="polyline.color"
      />
    </l-map>
  </div>
</template>

<script>
//import L from 'leaflet';
import { latLng } from "leaflet";
import { LMap, LTileLayer, LMarker, LTooltip, LPolyline } from "vue2-leaflet";

export default {
  name: "Example",
  props: ['events'],
  components: {
    LMap,
    LTileLayer,
    LMarker,
    LTooltip,
    LPolyline
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
      markerLatLng: [51.15636, 6.74023],
      markers: [],
      polyline: {
        latlngs: [],
        color: "blue"
      },
      glnsData: []
    };
  },
  computed: {
  },
  mounted() {
    this.$router.push("/")
    this.fillAllMarkers();
   // this.markers.push({loc: [51.15636, 6.74023], address: 'EECC'});
    //this.markers.push({loc: [51.184572107776745, 6.730605616962864], address: 'Schwein'});
    this.zoom = 12
  },
  methods: {
    fillAllMarkers(){
      //this.events.events.reverse();
      //_.invert(this.events.events);
      for (let key of Object.keys(this.events.events)) {
        //this.events.events[key].extension.ilmd.any[0].value
        //readpoints.push(this.events.events[key].readPoint.id);
        this.markers.push({loc: [this.convertGeoLocation(this.events.events[key].readPoint.id).lat, this.convertGeoLocation(this.events.events[key].readPoint.id).long], address: "GLN: 4016623000002\n" +
            "WOLF WURSTSPEZIALITÄTEN GMBH\n" +
            "Am Lindenhof 40\n" +
            "D-04626 Schmölln\n" +
            "Tel. 034491 31-0\n" +
            "Fax 034491 31-111\n"});
        this.polyline.latlngs.push([this.convertGeoLocation(this.events.events[key].readPoint.id).lat, this.convertGeoLocation(this.events.events[key].readPoint.id).long]);
        this.centerUpdate(latLng(this.convertGeoLocation(this.events.events[key].readPoint.id).lat,	this.convertGeoLocation(this.events.events[key].readPoint.id).long));
      }
      /*let glns = this.splitReadPointToGln(readpoints)
      glns.reverse()
      let str = ''
      str = ''+glns[0]+','+glns[1]+','+glns[2]+''
      this.getLocationDataApi(str)

       */
      //this.getLocationDataApi(this.splitReadPointToGln(readpoints));

    },
    convertGeoLocation(geoLocation) {
      const fields = geoLocation.split(":");
      let latLong = fields[1];
      const fields2 = latLong.split(",");
      return {"lat": fields2[0], "long": fields2[1]};
    },
    splitReadPointToGln(readpoints){
      let glns = [];
      for(let i = 0; i < readpoints.length; i++){
        const fields = readpoints[i].split(":");
        let sgln = fields[4];
        const fieldGln = sgln.split(".");
        glns.push(fieldGln[0] + fieldGln[1])
      }
      return glns;
     // return fieldGln[0] + fieldGln[1]
    },
    getLocationDataApi(glns){
      this.axios({
        url: "api/access/gln",
        params: {glns: glns},
        method: "GET"
      }).then(response => {
          for(let i = 0; i < response.data.length; i++){
            this.markers.push({loc: [response.data[i].longitude, response.data[i].latitude], address: response.data[i].address});
            this.polyline.latlngs.push([response.data[i].longitude, response.data[i].latitude]);

          }
        });
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
    }
  }
};
</script>
