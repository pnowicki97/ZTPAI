document.addEventListener('DOMContentLoaded', function() {
    const photoFileInput = document.getElementById('photoFile');
    const fileUploadText = document.getElementById('file-upload-text');
    const fileNameDisplay = document.getElementById('photo');

    if (photoFileInput && fileUploadText && fileNameDisplay) {
        photoFileInput.addEventListener('change', function() {
            if (this.files && this.files.length > 0) {
                fileNameDisplay.textContent = 'Selected photo: ' + this.files[0].name;
                fileUploadText.textContent = 'Change photo';
            } else {
                fileNameDisplay.textContent = '';
                fileUploadText.textContent = 'Choose photo';
            }
        });
    }

    flatpickr("#beginDate", {
        enableTime: true, // Enable time selection
        dateFormat: "Y-m-d H:i", // Format for LocalDateTime: Year-Month-Day Hour:Minute
        time_24hr: true // Use 24-hour format for time (optional)
    });

    // Initialize Flatpickr for endDate
    flatpickr("#endDate", {
        enableTime: true, // Enable time selection
        dateFormat: "Y-m-d H:i", // Format for LocalDateTime: Year-Month-Day Hour:Minute
        time_24hr: true // Use 24-hour format for time (optional)
    });
});
