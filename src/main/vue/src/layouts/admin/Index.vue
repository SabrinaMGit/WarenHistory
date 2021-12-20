<template>
  <v-app>
    <backend-app-bar />


    <backend-view />
  </v-app>
</template>

<script>
// Utilities
import {
  sync,
} from 'vuex-pathify'

export default {
  name: 'LayoutBackend',

  components: {
    BackendAppBar: () => import('./AppBar'),
    BackendView: () => import('./View'),
  },
}
</script>
