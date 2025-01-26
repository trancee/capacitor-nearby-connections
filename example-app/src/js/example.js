import { NearbyConnections } from '@capacitor-trancee/nearby-connections';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    NearbyConnections.echo({ value: inputValue })
}
