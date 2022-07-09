import request from "@/network/request";
const testAPI = require("@/apis");

export const makeDiscount = config=>request._post(testAPI.MAKE_DISCOUNT, config)
export const showDiscount = confif=>request._get(testAPI.SHOW_DISCOUNT, config)
export const deleteDiscount = config=>request._delete(testAPI.DELETE_DISCOUNT, config)