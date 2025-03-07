# 🛒 E-Commerce App
A simple Android e-commerce application that fetches products from the Fake Store API and displays them in a paginated RecyclerView.
Users can search, view product details, sort products, and place orders.

## 📌 Features
Fetch and display products from the Fake Store API.
Product details screen with image, description, price, and category.
Sort products by price.
Search functionality.
Order products (Will show a Toast).
Pagination support for better performance.
Error handling with proper UI feedback.
## 🛠 Tech Stack
Language: Kotlin
Architecture: MVVM
Networking: Retrofit
Image Loading: Picasso

## 🚀 Installation & Setup
1️⃣ Clone the Repository
bash
Copy
Edit
git clone https://github.com/SanjayKumar9121/HelyxonAndroidAssessment.git
OR manually Download ZIP and extract it.

2️⃣ Open the Project in Android Studio
Launch Android Studio.

Click "Open an Existing Project" and select the extracted project folder.

Wait for Gradle Sync to complete (this may take a few minutes).

⚙️ How to Build and Run the Project

▶️ Using an Emulator (Recommended)

Click on AVD Manager (in the toolbar).

Create a new Virtual Device (Pixel 5, etc.).

Select a system image (API Level 30+ recommended).

Click "Run" (▶️) to start the emulator.

▶️ Using a Physical Device

Enable Developer Mode on your phone.

Enable USB Debugging in Developer Settings.

Connect via USB cable and allow debugging access.

Select your device in Run Configurations and click "Run" (▶️).

## 🔥 Verify the App is Running
✅ Products should load from the Fake Store API.

✅ Click on a product to see details.

✅ Use search & sorting to test functionality.

✅ Try ordering (Toast message should appear).

## 💡 Troubleshooting
❌ Gradle Sync Issues?

Try "Invalidate Caches & Restart" from the File Menu.

Ensure you're using the latest Gradle version.

❌ API Data Not Loading?

Check your internet connection.

Ensure the Fake Store API is available.

❌ Emulator Running Slow?

Allocate more RAM via AVD Settings.

Use a real device for better performance.
