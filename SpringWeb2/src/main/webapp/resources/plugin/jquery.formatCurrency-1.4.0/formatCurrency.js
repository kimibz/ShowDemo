$(function() {
    var option = {
            symbol: "",
            negativeFormat: "-%s%n"
    };
    $("input.currency,select.currency,textarea.currency").live("focus", function() {
        var $this = $(this);
        if (/^[+-]?([1-9]\d{0,2}(?:,?\d{3})*|0)(?:\.\d{1,2})?$/.test($this.val())) {
            $this.toNumber(option);
        }
    });
    $("input.currency,select.currency,textarea.currency").live("blur", function() {
        var $this = $(this);
        if (/^[+-]?([1-9]\d{0,2}(?:,?\d{3})*|0)(?:\.\d{1,2})?$/.test($this.val())) {
            $this.formatCurrency(option);
        }
    });
});