export default {
  isValidValue(value) {
    return !(
      value === null ||
      value === undefined ||
      (typeof value === 'number' && isNaN(value)) ||
      (typeof value === 'string' && value.trim() === '')
    )
  }
}
