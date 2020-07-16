<template>
  <div>
    <v-btn-toggle>
      <v-btn color="black" @click="openSse('qqq')">1</v-btn>
      <v-btn color="purple" @click="openSse('www')">2</v-btn>
      <v-btn color="pink" @click="openSse('eee')">3</v-btn>
      <v-btn color="red" @click="eventSourceClose()">close</v-btn>
    </v-btn-toggle>
    <v-banner v-text="name">
    </v-banner>
    <v-list-group>
      <v-list-item v-for="(item, index) in transformedData" :key="index">
        {{item}}
      </v-list-item>
    </v-list-group>
  </div>
</template>

<script>

  export default {
    data: () => {
      return {
        name: 'psawesome',
        transformedData: [],
        es: undefined
      }
    },
    methods: {
      openSse(url) {
        this.transformedData = [];
        this.es = new EventSource(`http://psawesome.org:8080/api/v1/transformed/${url}`);
        this.name = `${"url is " + url}`;
        this.es.addEventListener("message", (msg) => {
          this.transformedData.push(msg.data);
        })
        this.es.onerror = (err) => {
          console.log(err);
          this.es.close();
        }
      },
      eventSourceClose() {
        try {
          this.es.close();
        }catch (e) {
          console.log(e);
        }
      }
    }
  }
</script>

<style scoped>

</style>