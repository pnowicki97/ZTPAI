function storeToken(token) {
    if (token) {
        localStorage.setItem("jwtToken", token);
        console.log("Token stored in localStorage:", token);
    } else {
        console.warn("No token found!");
    }
}

// Example function for making an API call using the token
function makeApiCall() {
    const token = localStorage.getItem("jwtToken");
    if (!token) {
        console.error("Token not found in localStorage!");
        return;
    }

    fetch('http://localhost:8080/**', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Failed to fetch data");
            }
        })
        .then(data => {
            console.log("Data from API:", data);
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

// Example of calling these functions
document.addEventListener("DOMContentLoaded", () => {
    const tokenFromServer = /*[[${token}]]*/ ''; // Injected by Thymeleaf
    storeToken(tokenFromServer);

    // Call the API after storing the token
    makeApiCall();
});

function login() {
    const credentials = {
        username: 'user',
        password: 'password'
    };

    fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
    .then(response => response.json())
    .then(token => {
        // Store the JWT token in localStorage
        localStorage.setItem('jwtToken', token);
    })
    .catch(error => console.error('Error during login:', error));
}

function fetchData() {
    const token = localStorage.getItem('jwtToken');

    if (token) {
        fetch('http://localhost:8080/users', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
        .then(response => response.json())
        .then(data => console.log('Protected data:', data))
        .catch(error => console.error('Error fetching data:', error));
    } else {
        console.log('No token found');
    }
}