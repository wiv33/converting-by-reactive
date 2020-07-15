<template>
  <div>
    <v-btn-toggle>
      <v-btn color="black" :click="openSse(`qqq`)">1</v-btn>
      <v-btn color="purple" :click="openSse(`www`)">2</v-btn>
      <v-btn color="pink" :click="openSse(`eee`)">3</v-btn>
    </v-btn-toggle>
    <v-banner v-text="name">

    </v-banner>
  </div>
</template>

<script>
  export default {
    data: () => {
      return {
        name: 'psawesome',
        transformedData: []
      }
    },
    methods: {
      openSse(url) {
        let es = new EventSource(`http://psawesome.org:8080/api/v1/transformed/${url}`);
        es.addEventListener("message", (msg) => {
          this.name = `${"url is " + url}`;
          this.transformedData.append(msg);
        })
      }
    }
  }
</script>

<style scoped>

</style>