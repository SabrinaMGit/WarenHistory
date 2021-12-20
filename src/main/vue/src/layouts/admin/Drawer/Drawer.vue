<template>
</template>

<script>
// Utilities
import {
  sync,
} from 'vuex-pathify'

export default {
  name: 'FrontendDrawer',

  computed: {
    ...sync('frontend/*'),
  },
}
</script>
