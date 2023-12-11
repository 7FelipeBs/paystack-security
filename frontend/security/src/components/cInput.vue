<template>
  <div>
    <label :for="idValue" class="block mb-2 text-sm text-gray-600 dark:text-gray-200">{{
      label
    }}</label>

    <input
      :type="type"
      :name="nameValue"
      :id="idValue"
      :ref="refValue"
      :placeholder="placeholder"
      hidden
      class="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-md dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
      v-model="value"
    />
  </div>
</template>

<script lang="js">
//   import { ref } from 'vue'

export default {
  name: 'CustomInput',

  props: {
    modelValue: {
      typeof: String,
      Number,
      default: null
    },

    nameValue: {
      typeof: String,
      default: ''
    },

    idValue: {
      typeof: String,
      default: ''
    },

    refValue: {
      typeof: String,
      default: ''
    },

    label: {
      typeof: String,
      default: ''
    },

    placeholder: {
      typeof: String,
      default: ''
    },

    type: {
      typeof: String,
      default: 'text'
    }
  },

  setup() {
    return {}
  },

  methods: {},

  computed: {
    value: {
      get() {
        return this.modelValue
      },
      set(value) {
        this.$emit('update:modelValue', value)
      }
    }
  }
}
</script>

<style lang=""></style>
