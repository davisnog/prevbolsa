// JavaScript Document 


$.widget("ui.form",{
		 _init:function(){
			 var object = this;
			 var form = this.element;
			 var inputs = form.find("input , select ,textarea");
			 
			  form.find("fieldset").addClass("ui-widget-content");
			  form.find("legend").addClass("ui-widget-header ui-corner-all");
			  form.addClass("ui-widget");
			
			  $.each(inputs,function(){
				$(this).addClass('ui-state-default ui-corner-all');
				
				if($(this).is(":reset ,:submit, :button")){
					object.buttons(this);
				}
				else if($(this).is(":checkbox"))
				object.checkboxes(this);
				else if($(this).is("input[type='text']")){
					$(this).css('height', '25px');
					object.textelements(this);
				}
				else if ($(this).is("textarea")||$(this).is("input[type='password']")){
					object.textelements(this);
				}
				else if($(this).is("select"))
				object.selector(this);
				
				if($(this).hasClass("date")){
					$.datepicker.setDefaults($.datepicker.regional["pt-BR"]);
					$(this).datepicker({
						changeMonth: true,
						changeYear: true
					});
				}
				
				
				});
			  var div = jQuery("<div />",{
							   css:{  
							   width:20,height:20,
							   margin:10,textAlign:"center"
							   }
							   
							   }).addClass("ui-state-default drag");
			  var no = Math.ceil(Math.random() * 4);
			  var holder = jQuery("<div />",{
								  id:'droppable',
								  text:"Drop the box with "+no+" here",
								 css:{
									 width:100,height:100,float:'right',fontWeight:'bold'}
								  }).addClass('ui-state-default');
			   $(form).find("fieldset");
			 
			     
			  
			   $(".drag").draggable({containment: 'parent'});
			   $("#droppable").droppable({
			accept:'#'+no,
			drop: function(event, ui) {
				$(this).addClass('ui-state-highlight').html("Right One");
				
				form.find(":submit").removeClass('ui-state-disabled').unbind('click');
				}
		});
			 $(".hover").hover(function(){
						  $(this).addClass("ui-state-hover"); 
						   },function(){ 
						  $(this).removeClass("ui-state-hover");  
						   });
			 
			 },
		 textelements:function(element){
			
			$(element).bind({
  			
 			  focusin: function() {
 			   $(this).toggleClass('ui-state-focus');
 				 },
			   focusout: function() {
 			    $(this).toggleClass('ui-state-focus');
 				 }	 
			  });
			 
			 },
		 buttons:function(element)
		 {
			if($(element).is(":submit"))
			{
				$(element).addClass("ui-priority-primary ui-corner-all ui-state-disabled hover");
			 $(element).bind("click",function(event)
			   {
				   event.preventDefault();
			   }); 
			}
			else if($(element).is(":reset"))
			$(element).addClass("ui-priority-secondary ui-corner-all hover");
			$(element).bind('mousedown mouseup', function() {
 			   $(this).toggleClass('ui-state-active');
 				 }
			  			 
			  ); 
		 },
		 
		 checkboxes:function(element){
		  $(element).parent("label").after("<span />");
		  var parent =  $(element).parent("label").next();
			 $(element).addClass("ui-helper-hidden");
				parent.css({width:16,height:16,display:"block"});
				
				 parent.wrap("<span class='ui-state-default ui-corner-all' style='display:inline-block;width:16px;height:16px;margin-right:5px;'/>");
			 
			 parent.parent().addClass('hover');
		  
			 
			 parent.parent("span").click(function(event){
						 $(this).toggleClass("ui-state-active");
						 parent.toggleClass("ui-icon ui-icon-check");
						$(element).click();
					
						});
			 
			 }
});
