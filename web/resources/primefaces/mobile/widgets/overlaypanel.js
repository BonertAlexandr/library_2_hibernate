PrimeFaces.widget.OverlayPanel=PrimeFaces.widget.BaseWidget.extend({init:function(a){this._super(a);this.cfg.visible=false;this.cfg.showEvent=this.cfg.showEvent||"click.overlaypanel";this.cfg.hideEvent=this.cfg.hideEvent||"click.overlaypanel";this.cfg.target=this.cfg.targetId?$(PrimeFaces.escapeClientId(this.cfg.targetId)):null;this.jq.panel({position:this.cfg.at,display:this.cfg.showEffect,dismissable:this.cfg.dismissable});if(this.cfg.dynamic){this.jq.append('<div class="ui-panel-inner"></div>');this.content=this.jq.children("div.ui-panel-inner")}this.bindEvents()},bindEvents:function(){var a=this;if(this.cfg.target){if(this.cfg.showEvent===this.cfg.hideEvent){this.cfg.target.on(this.cfg.showEvent,function(b){a.toggle()})}else{this.cfg.target.on(this.cfg.showEvent,function(b){a.show()}).on(this.cfg.hideEffect,function(b){a.hide()})}}},show:function(){if(!this.loaded&&this.cfg.dynamic){this.loadContents()}else{this._show()}},_show:function(){this.jq.panel("open");this.cfg.visible=true;if(this.cfg.onShow){this.cfg.onShow.call(this)}},hide:function(){this.jq.panel("close");this.cfg.visible=false;if(this.cfg.onHide){this.cfg.onHide.call(this)}},toggle:function(){if(this.cfg.visible){this.hide()}else{this.show()}},loadContents:function(){var b=this,a={source:this.id,process:this.id,update:this.id,params:[{name:this.id+"_contentLoad",value:true}],onsuccess:function(e,c,d){PrimeFaces.ajax.Response.handle(e,c,d,{widget:b,handle:function(f){this.content.html(f);this.loaded=true}});return true},oncomplete:function(){b._show()}};PrimeFaces.ajax.Request.handle(a)}});