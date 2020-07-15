<template>
  <div>
    <v-container>
      <hello-world/>
    </v-container>
  </div>
</template>

<script>
  import HelloWorld from './components/HelloWorld';

  export default {
    name: 'App',

    components: {
      HelloWorld,
    },

    data: () => ({
      //
    }),
  };
</script>

<style scoped>

</style>