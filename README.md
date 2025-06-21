# Minna No Hologram - Backend Monorepo

This repository is a monorepo for all backend (BE) and API services powering the Minna No Hologram platform. Each major backend or API is organized in its own top-level folder for clear separation and scalability.

## Repository Structure

- **minna-no-hologram-bff/**: Backend-for-Frontend (BFF) API for the end-user web/mobile app. Implements public-facing endpoints, OpenAPI spec, and business logic aggregation.
- **(future)** End-User Side BE APIs: Core business logic and data APIs for end-user features, separated from the BFF.
- **(future)** Operation Side APIs: Admin/operation APIs for internal tools, dashboards, and management.
- **(future)** Recommender API: Service for personalized recommendations, ranking, and content suggestions.
- **(future)** Video Processing Service: Handles video uploads, transcoding, thumbnail generation, and related media processing.
- **...**: Additional backend services can be added as new folders.

## Documentation

All technical and API documentation for this project is maintained in a separate repository:

- [minna-no-hologram-docs](https://github.com/hungkhanh0709/minna-no-hologram-docs)

Please refer to that repository for:
- API specifications (OpenAPI/Swagger)
- Architecture diagrams
- Feature documentation
- Developer guides and onboarding
- Contribution guidelines

> **Note:** This code repository only contains minimal setup and usage instructions. For full documentation, always check the docs repository above.

## Getting Started

Each service is self-contained in its folder. See the README in each subfolder for setup and usage instructions.

### Example: BFF API
- See [`minna-no-hologram-bff/README.md`](minna-no-hologram-bff/README.md) for details on the BFF API.

## Contributing

- Please open issues or pull requests for new features, bug fixes, or improvements.
- Use the provided PR template for all pull requests.
- Follow code style and documentation guidelines for each service.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

*This monorepo is designed for modular, scalable backend development. Add new services as your platform grows!*
