# Contributing to Football Tournament Management System

Thank you for your interest in contributing! This document provides guidelines for contributing to this project.

## Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/YOUR_USERNAME/FootballTournament.git`
3. Create a new branch: `git checkout -b feature/your-feature-name`
4. Make your changes
5. Test your changes thoroughly
6. Commit your changes: `git commit -m "Add your feature"`
7. Push to your fork: `git push origin feature/your-feature-name`
8. Create a Pull Request

## Development Setup

### Prerequisites
- Java JDK 20+
- MongoDB 4.0+
- Apache Tomcat 9.0
- Git

### Installation
```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/FootballTournament.git
cd FootballTournament

# Download required JAR files (see README.md)
# Compile the project
javac -cp "src/main/webapp/WEB-INF/lib/*;TOMCAT_PATH/lib/servlet-api.jar" -d build/classes src/main/java/**/*.java

# Deploy to Tomcat
# See SETUP_INSTRUCTIONS.md for details
```

## Code Style

- Use meaningful variable and method names
- Add comments for complex logic
- Follow Java naming conventions
- Keep methods focused and concise
- Write clean, readable code

## Testing

Before submitting a PR:
- Test all CRUD operations
- Verify MongoDB connections
- Check API endpoints
- Test UI functionality
- Ensure no console errors

## Pull Request Process

1. Update README.md with details of changes if needed
2. Update documentation for any new features
3. Ensure all tests pass
4. Get approval from maintainers

## Feature Requests

Open an issue with:
- Clear description of the feature
- Use cases
- Expected behavior
- Any relevant mockups or examples

## Bug Reports

Include:
- Description of the bug
- Steps to reproduce
- Expected behavior
- Actual behavior
- Screenshots if applicable
- Environment details (OS, Java version, etc.)

## Code of Conduct

- Be respectful and inclusive
- Welcome newcomers
- Focus on constructive feedback
- Help others learn and grow

## Questions?

Feel free to open an issue for any questions or clarifications.

Thank you for contributing! ⚽
