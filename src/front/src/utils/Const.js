const FormRules = {
    required: value => !!value || 'Required.',
    ip: value => /^(?!0)(?!.*\.$)((1?\d?\d|25[0-5]|2[0-4]\d)(\.|$)){4}$/.test(value) || 'IP format is incorrect.',
    port: value => /^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$/.test(value) || 'Port format is incorrect.',
    counter: ({min, max}) => {
        return value => ((min === undefined || (value && value.length >= min)) && (max === undefined || (value && value.length <= max))) || ('Incorrect length format ' + (min === undefined ? 0 : min) + '-' + (max === undefined ? 0 : max) + '.');
    },
};

export {
    FormRules
}