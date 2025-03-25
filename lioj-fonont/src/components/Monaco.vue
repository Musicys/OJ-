<template>
    <div id="code-editor" ref="codeEditorRef" style="min-height: 400px; height: 60vh" />
    <!--  <a-button @click="fillValue">填充值</a-button>-->
</template>

<script setup lang="ts">
import * as monaco from "monaco-editor";
import { onMounted, ref, toRaw, withDefaults, defineProps, watch, defineEmits } from "vue";

/**
 * 定义组件属性类型
 */
interface Props {
    value: string;
    language?: string;

}

/**
 * 给组件指定初始值
 */
const props = withDefaults(defineProps<Props>(), {
    value: () => "",
    language: () => "java",

});

const emit = defineEmits(['handleChange']);

const codeEditorRef = ref();
const codeEditor = ref();

// const fillValue = () => {
//   if (!codeEditor.value) {
//     return;
//   }
//   // 改变值
//   toRaw(codeEditor.value).setValue("新的值");
// };

watch(
    () => props.language,
    () => {
        if (codeEditor.value) {
            monaco.editor.setModelLanguage(
                toRaw(codeEditor.value).getModel(),
                props.language
            );
        }
    }
);

onMounted(() => {
    if (!codeEditorRef.value) {
        return;
    }
    // Hover on each property to see its docs!
    codeEditor.value = monaco.editor.create(codeEditorRef.value, {
        value: props.value,
        language: props.language,
        automaticLayout: true,
        colorDecorators: true,
        minimap: {
            enabled: true,
        },
        readOnly: false,
        theme: "vs-dark",
        // lineNumbers: "off",
        // roundedSelection: false,
        // scrollBeyondLastLine: false,
    });

    // 编辑 监听内容变化
    codeEditor.value.onDidChangeModelContent(() => {

        emit('handleChange', toRaw(codeEditor.value).getValue());

    });
});
</script>

<style scoped></style>