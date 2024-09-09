import {createI18n} from "vue-i18n";
import zh from "@/locales/zh.json";
import en from "@/locales/en.json";

export default new createI18n({
    legacy:false,
    locale:"zh",
    fallbackLocale:"zh",
    messages:{
        zh,
        en,
    },
});