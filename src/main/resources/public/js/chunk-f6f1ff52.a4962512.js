(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-f6f1ff52"],{"79fd":function(t,n,e){"use strict";e.r(n);var a=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("v-content",[e("v-fade-transition",{attrs:{mode:"out-in"}},[e("router-view")],1)],1)},i=[],o={name:"FrontendView"},r=o,s=e("2877"),d=e("6544"),c=e.n(d),u=(e("bd0c"),e("d10f")),f=u["a"].extend({name:"v-main",props:{tag:{type:String,default:"main"}},computed:{styles:function(){var t=this.$vuetify.application,n=t.bar,e=t.top,a=t.right,i=t.footer,o=t.insetFooter,r=t.bottom,s=t.left;return{paddingTop:"".concat(e+n,"px"),paddingRight:"".concat(a,"px"),paddingBottom:"".concat(i+o+r,"px"),paddingLeft:"".concat(s,"px")}}},render:function(t){var n={staticClass:"v-main",style:this.styles,ref:"main"};return t(this.tag,n,[t("div",{staticClass:"v-main__wrap"},this.$slots.default)])}}),l=e("d9bd"),p=f.extend({name:"v-main",created:function(){Object(l["d"])("v-content","v-main",this)},render:function(t){var n=f.options.render.call(this,t);return n.data.staticClass+=" v-content",n.children[0].data.staticClass+=" v-content__wrap",t(n.tag,n.data,n.children)}}),v=e("0789"),m=Object(s["a"])(r,a,i,!1,null,null,null),b=m.exports;c()(m,{VContent:p,VFadeTransition:v["c"]});n["default"]=b},bd0c:function(t,n,e){},d10f:function(t,n,e){"use strict";var a=e("2b0e");n["a"]=a["default"].extend({name:"ssr-bootable",data:function(){return{isBooted:!1}},mounted:function(){var t=this;window.requestAnimationFrame((function(){t.$el.setAttribute("data-booted","true"),t.isBooted=!0}))}})}}]);
//# sourceMappingURL=chunk-f6f1ff52.a4962512.js.map