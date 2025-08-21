# 📁 Android File Manager

A modern, lightweight file manager application for Android built with Kotlin, featuring an intuitive interface and essential file management capabilities.

## ✨ Features

- **Dual View Modes**: Switch between grid and linear list layouts for optimal file browsing
- **File Type Recognition**: Automatic detection and categorization of images, videos, audio files, and archives
- **File Operations**: Create, delete, and organize files and folders with ease
- **Smart File Opening**: Automatically launches appropriate apps for different file types
- **Material Design**: Clean, modern UI following Android design guidelines
- **Navigation**: Seamless folder navigation with back stack support
- **File Type Icons**: Visual indicators for different file formats (images, videos, audio, ZIP, RAR, etc.)

## 🛠️ Technical Stack

- **Language**: Kotlin
- **UI Framework**: Android Views with View Binding
- **Architecture**: Fragment-based navigation
- **Layout**: RecyclerView with GridLayoutManager
- **File Handling**: Android File API with FileProvider for secure file sharing
- **Design Pattern**: Adapter pattern for RecyclerView implementation

## 📱 Screenshots

<img width="390" height="819" alt="image" src="https://github.com/user-attachments/assets/1a84da41-d69b-470d-b0cc-8c93b3d64042" />  <img width="383" height="810" alt="image" src="https://github.com/user-attachments/assets/1b1fb42d-3132-4834-a2a2-e9210f8c3869" />

<img width="365" height="772" alt="image" src="https://github.com/user-attachments/assets/0b7f1689-a0fe-4075-bbaa-923e74a0a3b9" />  <img width="374" height="744" alt="image" src="https://github.com/user-attachments/assets/6ac8ea12-1c1d-43e2-b5e6-5b8636c5f149" />




## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Minimum SDK: API 21 (Android 5.0)
- Target SDK: API 34

### Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/android-file-manager.git
```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Run the app on an emulator or physical device

## 📋 Permissions

The app requires the following permissions:
- `READ_EXTERNAL_STORAGE` - To browse and read files
- `WRITE_EXTERNAL_STORAGE` - To create and modify files (Android 9 and below)

## 🏗️ Architecture

The app follows a clean architecture pattern:

- **MainActivity**: Entry point and fragment container
- **FragmentFile**: Handles file browsing and operations for each directory
- **fileAdapter**: RecyclerView adapter managing file display and interactions
- **File Operations**: Create folders, delete files, and handle file interactions

## 📂 Key Components

### File Type Detection
The app intelligently detects file types using MIME type recognition:
- Images (JPG, PNG, GIF, etc.)
- Videos (MP4, AVI, etc.)
- Audio files (MP3, WAV, etc.)
- Archives (ZIP, RAR)
- Generic files

### View Switching
Users can toggle between:
- **Grid View**: Compact view showing more files at once
- **List View**: Detailed linear view for easier navigation

## 🔧 Customization

The app supports easy customization of:
- File type icons
- View layouts
- Color schemes
- Supported file formats

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Contact

Your Name - your.email@example.com

Project Link: [https://github.com/yourusername/android-file-manager](https://github.com/yourusername/android-file-manager)

## 🙏 Acknowledgments

- Android development community
- Material Design guidelines
- Kotlin documentation

---

⭐ If you found this project helpful, please give it a star!
