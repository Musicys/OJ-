<template>
    <H1 style="color:rgba(255, 255, 255, .6);">{{ porps.title }}</H1>
    <div class="mkder">


        <Editor :value="value" :plugins="plugins" @change="handleChange" />
        <Viewer :value="value"></Viewer>
    </div>

</template>

<script setup lang="ts">
import gfm from "@bytemd/plugin-gfm";
import highlight from "@bytemd/plugin-highlight";
import { Editor, Viewer } from "@bytemd/vue-next";
import { onMounted, ref, defineEmits, watch } from "vue";


const porps = defineProps(["ste", "title"])

const emit = defineEmits(['custom-event']);

const plugins = [
    gfm(),
    highlight(),
    // Add more plugins here
];
const value = ref("")


const handleChange = (v: string) => {

    value.value = v
    emit('custom-event', v);
};

watch(porps, (value, newvalue) => {
    handleChange(value.ste)
})

onMounted(() => {

    value.value = porps.ste
})
</script>

<style scoped>
.mkder {
    width: 100%;
    display: flex;
    background: white;
    border: 1px solid black;

    margin-bottom: 1em;
}
</style>