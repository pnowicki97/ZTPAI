// Replace with your actual JWT token
const jwtToken = "your_jwt_token_here"; // Replace this with the actual token

// Replace with your backend endpoint URL
const backendUrl = "http://localhost:8080/api/resource";

// Add an event listener to the button
document.getElementById("sendRequest").addEventListener("click", () => {
    // Clear previous output
    document.getElementById("responseOutput").textContent = "Loading...";

    // Make the fetch request
    fetch(backendUrl, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${jwtToken}`, // Add the Authorization header
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); // Assuming the response is JSON
        })
        .then(data => {
            // Display the response in the output element
            document.getElementById("responseOutput").textContent = JSON.stringify(data, null, 2);
        })
        .catch(error => {
            // Display any errors
            document.getElementById("responseOutput").textContent = `Error: ${error.message}`;
        });
});
