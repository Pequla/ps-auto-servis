const baseUrl = "http://ps.pequla.com:8080/api"

// Dynamic menu
const session = window.localStorage.getItem("name");
if (session && !window.location.href.includes("logout.html")) {
    document.querySelectorAll(".action-logout").forEach(e => e.hidden = false);
    document.querySelectorAll(".action-login").forEach(e => e.hidden = true);
}

function alertError(e) {
    alert(e);
    window.location.href = `/index.html?from=${window.location.href}&reason=${e.message}`;
}

function formatDate(str) {
    const date = new Date(str);
    return ` ${pad(date.getHours(), 2)}:${pad(date.getMinutes(), 2)} ${date.getDay()}.${date.getMonth()}.${date.getFullYear()}.`
}

function pad(n, width, z) {
    z = z || '0';
    n = n + '';
    return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
}

function login() {
    const email = document.getElementById("email").value
    const password = document.getElementById("password").value

    if (!email || !password) {
        alert("Oba polja su obavezna");
        return;
    }

    fetch(baseUrl + "/login?username=" + email + "&password=" + password, {
        method: "POST"
    }).then(rsp => {
        if (rsp.ok) return rsp.json();
        throw new Error("Login rejected");
    }).then(data => {
        window.localStorage.setItem("access", data.accessToken);
        window.localStorage.setItem("refresh", data.refreshToken);

        // Getting the current user
        getSelfUser().then(data => {
            // Setting username
            window.localStorage.setItem("name", data.name);

            // Go back to home
            const url = new URL(window.location.href);
            // Example: /login.html?return=account.html
            const returnTo = url.searchParams.get("return");
            if (returnTo) {
                window.location.replace(returnTo);
            } else {
                window.location.replace("/app/user/account.html");
            }
        });
    }).catch(e => {
        console.error(e);
        refreshToken();
    });
}

function getSelfUser() {
    return fetch(baseUrl + "/user/me", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    }).then(rsp => {
        if (rsp.ok) return rsp.json();
        throw new Error("Failed retrieving user data");
    });
}

function refreshToken() {
    fetch(baseUrl + "/token/refresh", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("refresh")
        }
    }).then(rsp => {
        if (rsp.ok) return rsp.json();
        throw new Error("Login required");
    }).then(data => {
        window.localStorage.setItem("access", data.accessToken);
        window.localStorage.setItem("refresh", data.refreshToken);

        // Getting the current user
        getSelfUser().then(data => {
            // Setting username
            window.localStorage.setItem("username", data.name);
        });
    }).catch(e => {
        console.error(e);
        window.location.replace("/app/user/login.html");
    })
}

function handleResponse(rsp, msg) {
    if (rsp.ok)
        return rsp.json();
    if (rsp.status === 403) {
        // Forbidden
        refreshToken();
        window.location.reload();
    }
    if (rsp.status === 404) {
        window.history.go(-1);
    }
    throw new Error(msg);
}

async function getVehicleList() {
    const rsp = await fetch(baseUrl + "/vehicle", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    });
    return handleResponse(rsp, "Vehicle list retrieval failed");
}

async function getVehicleForId(id) {
    const rsp = await fetch(baseUrl + "/vehicle/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    });
    return handleResponse(rsp, `Vehicle for id ${id} lookup failed`);
}

async function getCustomerList() {
    const rsp = await fetch(baseUrl + "/customer", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    });
    return handleResponse(rsp, "Customer list retrieval failed");
}

async function getCustomerForId(id) {
    const rsp = await fetch(baseUrl + "/customer/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    });
    return handleResponse(rsp, `Customer for id ${id} retrieval failed`);
}

async function getVehicleListByCustomerId(id) {
    const rsp = await fetch(baseUrl + "/vehicle/customer/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    });
    return handleResponse(rsp, "Customer list retrieval failed");
}

async function saveCustomer(payload) {
    // Input correction
    if (payload.taxId === "") {
        payload.taxId = null;
    }

    const rsp = await fetch(baseUrl + "/customer", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        },
        body: JSON.stringify(payload)
    });
    return handleResponse(rsp, "Saving a new customer failed");
}

async function updateCustomer(id, payload) {
    // Input correction
    if (payload.taxId === "") {
        payload.taxId = null;
    }

    // Must be set to correct record id
    payload.id = id;

    const rsp = await fetch(baseUrl + "/customer/" + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        },
        body: JSON.stringify(payload)
    });
    return handleResponse(rsp, "Updating customer failed");
}

function deleteCustomer(id) {
    fetch(baseUrl + "/customer/" + id, {
        method: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    }).then(rsp => {
        if (rsp.status !== 204) {
            throw new Error("Failed to delete customer")
        }
    }).catch(e => alertError(e));
}

async function getInventoryItemList() {
    const rsp = await fetch(baseUrl + "/inventory", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    });
    return handleResponse(rsp, "Inventory list retrieval failed");
}

// ==== INVENTORY ITEM ====

async function getInventoryItemForId(id) {
    const rsp = await fetch(baseUrl + "/inventory/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    });
    return handleResponse(rsp, `Item for id ${id} retrieval failed`);
}

async function saveInventoryItem(payload) {
    // Input correction
    if (payload.available === "") {
        payload.available = null;
    }

    const rsp = await fetch(baseUrl + "/inventory", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        },
        body: JSON.stringify(payload)
    });
    return handleResponse(rsp, "Saving a new inventory item failed");
}

async function updateInventoryItem(id, payload) {
    // Input correction
    if (payload.available === "") {
        payload.available = null;
    }

    // Must be set to correct record id
    payload.id = id;

    const rsp = await fetch(baseUrl + "/inventory/" + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        },
        body: JSON.stringify(payload)
    });
    return handleResponse(rsp, "Updating inventory item failed");
}

function deleteInventoryItem(id) {
    fetch(baseUrl + "/inventory/" + id, {
        method: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    }).then(rsp => {
        if (rsp.status !== 204) {
            throw new Error("Failed to delete inventory item")
        }
    }).catch(e => alertError(e));
}

async function getWorkOrderListForVehicle(id) {
    const rsp = await fetch(baseUrl + "/order/vehicle/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    });
    return handleResponse(rsp, "Work order list retrieval failed");
}

async function getWorkOrderListForVehicleVin(vin) {
    const rsp = await fetch(baseUrl + "/order/vehicle/vin/" + vin, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    return handleResponse(rsp, "Podaci za vozilo nisu pronadjeni");
}

async function getWorkOrderList() {
    const rsp = await fetch(baseUrl + "/order", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    });
    return handleResponse(rsp, "Podaci za radni nalog nisu pronadjeni");
}

async function getWorkOrderById(id) {
    const rsp = await fetch(baseUrl + "/order/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem("access")
        }
    });
    return handleResponse(rsp, "Podaci za radni nalog nisu pronadjeni");
}