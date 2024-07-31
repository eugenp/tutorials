import axios from "axios";

export default function Logout() {

  async function onClick() {
    const response = await axios.post(
      "/bff/logout",
      {},
      {
        headers: {
          "X-POST-LOGOUT-SUCCESS-URI": process.env.NEXT_PUBLIC_BASE_URI,
        },
      }
    );
    window.location.href = response.headers["location"];
  }

  return (
    <button type="submit" onClick={onClick}>
      Logout
    </button>
  );
}
