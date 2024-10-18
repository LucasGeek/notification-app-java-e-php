<?php

namespace App\Services;

use App\Models\NotificationType;
use App\Repositories\NotificationTypeRepository;

class NotificationTypeService
{
    private NotificationTypeRepository $notificationTypeRepository;

    public function __construct(NotificationTypeRepository $notificationTypeRepository)
    {
        $this->notificationTypeRepository = $notificationTypeRepository;
    }

    public function create(array $data): NotificationType
    {
        return $this->notificationTypeRepository->create($data);
    }

    public function update(NotificationType $notificationType, array $data): bool
    {
        return $this->notificationTypeRepository->update($notificationType, $data);
    }

    public function delete(NotificationType $notificationType): bool
    {
        return $this->notificationTypeRepository->delete($notificationType);
    }

    public function findById(int $id): ?NotificationType
    {
        return $this->notificationTypeRepository->findById($id);
    }

    public function getByUser(int $userId): array
    {
        return $this->notificationTypeRepository->findByUserId($userId);
    }
}
