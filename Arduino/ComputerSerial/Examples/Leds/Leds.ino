#include <ComputerSerial.h>

unsigned long time;
boolean h = true;

//Constants
static const int PIN_LED = 2;
static const int PIN_SOUND = 3;
static const int RESET_PIN = 4;
// static const int PIN_VIBRATION = 5;
static const int PIN_DEBUG_LED = 13;

static const int PIN_SCREEN_A = 7;
static const int PIN_SCREEN_B = 8;
static const int PIN_SCREEN_1 = 9;
static const int PIN_SCREEN_2 = 10;
static const int PIN_SCREEN_3 = 11;
static const int PIN_SCREEN_4 = 12;

//Global variables
static ComputerSerial comp;

void setup()
{
	//Initialize computer serial class
	comp.begin(115200);
	
	comp.setDeviceName("uCSS");
	comp.setDeviceVersion("v0.1a");
	
	// LED
	comp.addDeviceService("LED_LAMP", "2,13");
	pinMode(PIN_LED, OUTPUT);
	pinMode(PIN_DEBUG_LED, OUTPUT);

	//Download links
	comp.addDeviceDownloadLink("http://folk.ntnu.no/svarvaa/utils/pro2www/#appId1", "OSNAP Jacket");
	
	
	pinMode(5, OUTPUT);
	pinMode(6, OUTPUT);
	pinMode(13, OUTPUT);
	
	time = millis();
}

void loop()
{
	if(h) {
		if(millis() >= time+3000) {
			digitalWrite(5, HIGH);
			time = millis();
			h=false;
		}
		else if(millis() >= time+2000) {
			digitalWrite(6, HIGH);
		}
		else if(millis() >= time+1000) {
			digitalWrite(13, HIGH);
		}
	}
	else {
		if(millis() >= time+3000) {
			digitalWrite(5, LOW);
			time = millis();
			h=true;
		}
		else if(millis() >= time+2000) {
			digitalWrite(6, LOW);
		}
		else if(millis() >= time+1000) {
			digitalWrite(13, LOW);
		}
	}
}

void serialEvent()
{
	comp.serialEvent();
}
