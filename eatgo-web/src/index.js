(async () => {
    const url = "http://localhost:8080/restourants";
    const response = await fetch(url);
    const restourants = await response.json();

    const element = document.getElementById("app");
    element.innerHTML = `
        ${restourants.map(restourant => `
        <p>
            ${restourant.id}
            ${restourant.name}
            ${restourant.address}
        </p>
        `).join('')}
    `;
})();
