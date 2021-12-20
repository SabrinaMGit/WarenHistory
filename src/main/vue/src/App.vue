<template>
  <v-fade-transition mode="out-in">
    <router-view />
  </v-fade-transition>
</template>

<script>
  export default {
    name: 'App',

    metaInfo () {
      return {
        title: 'Intelli-Pack',
        titleTemplate: '%s',
        htmlAttrs: { lang: 'de' },
        meta: [{ charset: 'utf-8' }],
      }
    },
  }
</script>
