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