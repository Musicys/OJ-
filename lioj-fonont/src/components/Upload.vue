<template>
    <div class="update">

        <div class="box">
            <a-space direction="vertical">
                <a-upload action="/" :fileList="file ? [file] : []" :show-file-list="false" @change="onChange"
                    @progress="onProgress">
                    <template #upload-button>
                        <div :class="`arco-upload-list-item${file && file.status === 'error' ? ' arco-upload-list-item-error' : ''
                            }`">
                            <div class="arco-upload-list-picture custom-upload-avatar" v-if="file && file.url">
                                <img :src="file.url" />
                                <div class="arco-upload-list-picture-mask">
                                    <IconEdit />
                                </div>
                                <a-progress v-if="file.status === 'uploading' && file.percent < 100"
                                    :percent="file.percent" type="circle" size="mini" :style="{
                                        position: 'absolute',
                                        left: '50%',
                                        top: '50%',
                                        transform: 'translateX(-50%) translateY(-50%)',
                                    }" />
                            </div>
                            <div class="arco-upload-picture-card" v-else>
                                <div class="arco-upload-picture-card-text">
                                    <IconPlus />
                                    <div style="margin-top: 10px; font-weight: 600">Upload</div>
                                </div>
                            </div>
                        </div>
                    </template>
                </a-upload>
            </a-space>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { IconEdit, IconPlus } from '@arco-design/web-vue/es/icon';
import { ref } from 'vue';

const file = ref();
const emit = defineEmits(["updateFile"])
const onChange = (_, currentFile) => {
    file.value = {
        ...currentFile,
        // url: URL.createObjectURL(currentFile.file),
    };


};
const onProgress = (currentFile) => {
    file.value = currentFile;
    console.log(file.value);
    emit("updateFile", file.value)
};
</script>


<style scoped>
.update {
    display: flex;

    justify-content: center;
    margin-bottom: 1em;
    align-items: center;
    width: 100%;
}
</style>