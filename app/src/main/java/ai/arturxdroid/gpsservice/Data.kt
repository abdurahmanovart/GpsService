package ai.arturxdroid.gpsservice

data class GpsData(val size: Int, val coordinatesList: List<CoordinatesData>)

data class CoordinatesData(val ts: String, val lat: String, val lon: String)