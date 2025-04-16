## 📦 Modules Breakdown

### 🔹 Model
Defines the `DiscoveryItem` data class which matches the API schema.

### 🔹 Network
Contains `BookDiscoveryApi` and `BookDiscoveryService`.

### 🔹 Repository
Manages data flow logic:
- Checks if cached data is valid (2-hour policy)
- Fetches from API if outdated
- Saves fresh data and timestamp

### 🔹 ViewModel
- Holds state using `StateFlow`
- Exposes data to UI
- Controls loading logic

### 🔹 Screen
- Composable screen (`HomeScreen`)
- Uses `LazyColumn` to display cards
- Loads images using `Coil`

## 🧠 Why DataStore?

DataStore is lightweight, faster than SharedPreferences, and perfect for small key-value JSON and timestamp caching.
 
## 🧪 Testing Strategy (remain)

- Mock Repository for ViewModel unit tests
- Use fake API responses to test offline/online states