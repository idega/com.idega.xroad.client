jQuery(window).load(function () {
	jQuery("#serviceProviderForm").validate({
		rules: {
			"serviceProviderForm:providerId" : {
				required:	true,
				number:		true
			},
			"serviceProviderForm:url" : {
				required:	true
			}
		},
		messages: {
			"serviceProviderForm:providerId":	ServiceProviderEditorHelper.FIELD_IS_REQUIRED,
			"serviceProviderForm:url":			ServiceProviderEditorHelper.FIELD_IS_REQUIRED
		}
	});
});

var ServiceProviderEditorHelper = {
		FIELD_IS_REQUIRED:	"Field is required!",
		SAVING:				"Saving..."
}