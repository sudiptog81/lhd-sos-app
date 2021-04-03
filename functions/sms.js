const axios = require("axios").default;
const Twilio = require("twilio");

const accountSid = process.env.TWILIO_ACCOUNT_SID;
const authToken = process.env.TWILIO_AUTH_TOKEN;

const client = Twilio(accountSid, authToken);

exports.handler = async (event, context) => {
  try {
    if (!event.body) throw Error("No Request Body");

    const reqBody = JSON.parse(event.body);

    const geocodedResponse = await axios.get(
      "https://maps.googleapis.com/maps/api/geocode/json",
      {
        params: {
          latlng: reqBody.message,
          key: process.env.MAPS_API_KEY,
        },
      }
    );

    if (!geocodedResponse.data.results) throw Error("No Address Found");

    const address = geocodedResponse.data.results[0]["formatted_address"];

    const response = await client.messages.create({
      body: `Please Help Me! I am near ${address}.`,
      from: process.env.TWILIO_NUMBER,
      to: event.body.to || process.env.TO_NUMBER,
    });

    return {
      statusCode: 200,
      body: JSON.stringify({ message: response.sid }),
    };
  } catch (error) {
    return {
      statusCode: 500,
      body: JSON.stringify({ error }),
    };
  }
};
