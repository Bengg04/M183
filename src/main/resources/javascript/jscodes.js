function closeDialogIfSucess(xhr, status, args, dialogWidget, dialogId) {
	if (args.validationFailed || args.KEEP_DIALOG_OPENED) {
		dialogWidget.jq.effect("bounce", {times : 4, distance : 20}, 100);
	} else {
		dialogWidget.hide();
	}
}

function handleLoginResult(xhr, status, args, dialogWidget, dialogId) {
	if(args.validationFailed || args.KEEP_DIALOG_OPENED) {
		console.log('failed');
		dialogWidget.jq.effect("shake", {times:5}, 100);
		
	}
}