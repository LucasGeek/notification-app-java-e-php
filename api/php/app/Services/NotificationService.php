<?php

namespace App\Services;

use App\Models\Notification;
use App\Repositories\NotificationRepository;

class NotificationService
{
    private NotificationRepository $notificationRepository;

    public function __construct(NotificationRepository $notificationRepository)
    {
        $this->notificationRepository = $notificationRepository;
    }

    public function create(array $data): Notification
    {
        return $this->notificationRepository->create($data);
    }

    public function update(Notification $notification, array $data): bool
    {
        return $this->notificationRepository->update($notification, $data);
    }

    public function delete(Notification $notification): bool
    {
        return $this->notificationRepository->delete($notification);
    }

    public function getByUser(int $userId): array
    {
        return $this->notificationRepository->findByUserId($userId);
    }

    public function getByUserAndType(int $userId, int $typeId): array
    {
        return $this->notificationRepository->findByUserIdAndType($userId, $typeId);
    }

    public function findById(int $id): ?Notification
    {
        return $this->notificationRepository->findById($id);
    }
}
