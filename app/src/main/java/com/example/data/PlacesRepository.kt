package com.example.data

import com.example.R
import com.example.model.Place

object PlacesRepository {
    val places = listOf(
        Place(
            id = "p1",
            nameEn = "Dabbe Falls",
            nameKn = "ಡಬ್ಬೆ ಜಲಪಾತ",
            descriptionEn = "A beautiful, lush green waterfall hidden deep in the dense forests of the Western Ghats. Water cascading down rocky cliffs. Requires trekking through challenging terrain.",
            descriptionKn = "ಪಶ್ಚಿಮ ಘಟ್ಟಗಳ ದಟ್ಟವಾದ ಕಾಡುಗಳಲ್ಲಿ ಅಡಗಿರುವ ಸುಂದರವಾದ, ಹಸಿರು ಜಲಪಾತ.",
            location = "Sagara, Shivamogga",
            category = "Waterfall",
            bestTimeEn = "September to January",
            bestTimeKn = "ಸೆಪ್ಟೆಂಬರ್ ನಿಂದ ಜನವರಿ",
            entryFeeEn = "Forest Dept permission required",
            entryFeeKn = "ಅರಣ್ಯ ಇಲಾಖೆ ಅನುಮತಿ ಅಗತ್ಯವಿದೆ",
            travelCostEn = "₹200 - ₹500",
            travelCostKn = "₹200 - ₹500",
            distance = "30 km from Sagara",
            routeEn = "Shivamogga -> Sagara -> Hosagadde -> Dabbe",
            routeKn = "ಶಿವಮೊಗ್ಗ -> ಸಾಗರ -> ಹೊಸಗದ್ದೆ -> ಡಬ್ಬೆ",
            latitude = 14.15,
            longitude = 74.85,
            imageResId = R.drawable.img_dabbe
        ),
        Place(
            id = "p2",
            nameEn = "Honnemaradu",
            nameKn = "ಹೊನ್ನೇಮರಡು",
            descriptionEn = "A scenic, peaceful backwater reservoir in a valley, surrounded by green hills on the Sharavathi River. Great for kayaking and coracle rides.",
            descriptionKn = "ಶರಾವತಿ ನದಿಯ ಹಿನ್ನೀರಿನಲ್ಲಿರುವ ಒಂದು ಸುಂದರವಾದ, ಶಾಂತವಾದ ತಾಣ.",
            location = "Sagara, Shivamogga",
            category = "Nature",
            bestTimeEn = "October to March",
            bestTimeKn = "ಅಕ್ಟೋಬರ್ ನಿಂದ ಮಾರ್ಚ್",
            entryFeeEn = "Activity charges apply",
            entryFeeKn = "ಚಟುವಟಿಕೆ ಶುಲ್ಕಗಳು ಅನ್ವಯಿಸುತ್ತವೆ",
            travelCostEn = "₹500 - ₹1000",
            travelCostKn = "₹500 - ₹1000",
            distance = "25 km from Sagara",
            routeEn = "Shivamogga -> Sagara -> Talaguppa -> Honnemaradu",
            routeKn = "ಶಿವಮೊಗ್ಗ -> ಸಾಗರ -> ತಾಳಗುಪ್ಪ -> ಹೊನ್ನೇಮರಡು",
            latitude = 14.23,
            longitude = 74.87,
            imageResId = R.drawable.img_honnemaradu
        ),
        Place(
            id = "p3",
            nameEn = "Jogigundi Falls",
            nameKn = "ಜೋಗಿಗುಂಡಿ ಜಲಪಾತ",
            descriptionEn = "A unique waterfall falling out of a cave into a beautiful natural pool. Deep green forest setting.",
            descriptionKn = "ಗುಹೆಯಿಂದ ಸುಂದರವಾದ ನೈಸರ್ಗಿಕ ಕೊಳಕ್ಕೆ ಬೀಳುವ ವಿಶಿಷ್ಟ ಜಲಪಾತ.",
            location = "Agumbe, Shivamogga",
            category = "Waterfall",
            bestTimeEn = "Monsoon and Post-monsoon",
            bestTimeKn = "ಮಳೆಗಾಲ ಮತ್ತು ಮಳೆಗಾಲದ ನಂತರ",
            entryFeeEn = "Free",
            entryFeeKn = "ಉಚಿತ",
            travelCostEn = "₹300 - ₹600",
            travelCostKn = "₹300 - ₹600",
            distance = "4 km from Agumbe",
            routeEn = "Shivamogga -> Thirthahalli -> Agumbe -> Jogigundi",
            routeKn = "ಶಿವಮೊಗ್ಗ -> ತೀರ್ಥಹಳ್ಳಿ -> ಆಗುಂಬೆ -> ಜೋಗಿಗುಂಡಿ",
            latitude = 13.50,
            longitude = 75.09,
            imageResId = R.drawable.img_jogigundi
        ),
        Place(
            id = "p4",
            nameEn = "Soormane Falls",
            nameKn = "ಸೂರ್ಮನೆ ಜಲಪಾತ",
            descriptionEn = "A small, beautiful cascading waterfall forming a calm stream in a dense, green forest near Kalasa (close to Shivamogga border).",
            descriptionKn = "ದಟ್ಟವಾದ ಹಸಿರು ಕಾಡಿನಲ್ಲಿ ಹರಿಯುವ ಸುಂದರವಾದ ಚಿಕ್ಕ ಜಲಪಾತ.",
            location = "Kalasa region",
            category = "Waterfall",
            bestTimeEn = "August to December",
            bestTimeKn = "ಆಗಸ್ಟ್ ನಿಂದ ಡಿಸೆಂಬರ್",
            entryFeeEn = "Nominal fee by locals",
            entryFeeKn = "ಸ್ಥಳೀಯರಿಂದ ನಾಮಮಾತ್ರ ಶುಲ್ಕ",
            travelCostEn = "₹400 - ₹800",
            travelCostKn = "₹400 - ₹800",
            distance = "Near Hornadu",
            routeEn = "Shivamogga -> Balehonnur -> Kalasa -> Soormane",
            routeKn = "ಶಿವಮೊಗ್ಗ -> ಬಾಳೆಹೊನ್ನೂರು -> ಕಳಸ -> ಸೂರ್ಮನೆ",
            latitude = 13.25,
            longitude = 75.36,
            imageResId = R.drawable.img_soormane
        )
    )

    fun getPlaceById(id: String): Place? = places.find { it.id == id }
}
