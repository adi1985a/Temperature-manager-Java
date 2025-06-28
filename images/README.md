# 🖼️ Graphics Integration Guide

## 📁 Folder Structure for Images

Place your image files in the following structure:

```
images/
├── icons/           # Application icons
│   ├── app_icon.png
│   ├── temperature.png
│   └── export.png
├── backgrounds/     # Background images
│   ├── main_bg.jpg
│   └── panel_bg.png
├── logos/          # Company/application logos
│   ├── company_logo.png
│   └── app_logo.jpg
├── charts/         # Generated chart images
│   └── temperature_chart.png
└── README.md       # This file
```

## 🎯 Recommended Image Specifications

### Icons
- **Size**: 16x16, 32x32, 64x64 pixels
- **Format**: PNG (preferred) or ICO
- **Style**: Flat design, consistent with application theme
- **Colors**: Match application color scheme (blue theme)

### Backgrounds
- **Size**: 1920x1080 or 1280x720 pixels
- **Format**: JPG or PNG
- **Style**: Subtle, professional, not distracting
- **Opacity**: Consider semi-transparent overlays

### Logos
- **Size**: 200x200 pixels minimum
- **Format**: PNG with transparency
- **Style**: Professional, clean design
- **Colors**: Should work on both light and dark backgrounds

### Charts
- **Size**: 800x600 pixels
- **Format**: PNG or JPG
- **Style**: Clear, readable data visualization
- **Colors**: High contrast for readability

## 🔧 How to Integrate Images in the Application

### 1. Loading Images in Java
```java
// Example code to load an image
ImageIcon icon = new ImageIcon("images/icons/temperature.png");
JButton button = new JButton(icon);
```

### 2. Setting Application Icon
```java
// Set application icon
ImageIcon appIcon = new ImageIcon("images/icons/app_icon.png");
setIconImage(appIcon.getImage());
```

### 3. Background Images
```java
// Set background image for panels
ImageIcon bgImage = new ImageIcon("images/backgrounds/main_bg.jpg");
JLabel backgroundLabel = new JLabel(bgImage);
```

## 📋 Image Naming Convention

Use descriptive, lowercase names with underscores:
- `temperature_icon.png`
- `export_button.png`
- `main_background.jpg`
- `company_logo.png`
- `chart_temperature.png`

## 🎨 Color Scheme Reference

Application uses these colors:
- **Primary Blue**: #3498db (52, 152, 219)
- **Secondary Blue**: #2980b9 (41, 128, 185)
- **Success Green**: #2ecc71 (46, 204, 113)
- **Warning Orange**: #e67e22 (230, 126, 34)
- **Error Red**: #e74c3c (231, 76, 60)
- **Background Gray**: #ecf0f1 (236, 240, 241)
- **Panel White**: #ffffff (255, 255, 255)

## ⚠️ Important Notes

1. **File Size**: Keep images under 2MB for optimal performance
2. **Resolution**: Use 72-300 DPI for screen display
3. **Formats**: PNG for icons/logos, JPG for photos/backgrounds
4. **Backup**: Always keep original high-resolution versions
5. **Testing**: Test images on different screen resolutions

## 🚀 Quick Start

1. Create the folder structure above
2. Add your images to appropriate folders
3. Update the Java code to reference your images
4. Test the application with your graphics
5. Optimize images if needed for performance

## 📞 Support

For questions about image integration:
- Check the main README.md file
- Review Java Swing documentation
- Contact the development team 