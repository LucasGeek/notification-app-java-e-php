<?php

namespace App\Repositories;

use App\Models\Notification;

interface NotificationRepository
{
    public function create(array $data): Notification;
    public function findById(int $id): ?Notification;
    public function update(Notification $notification, array $data): bool;
    public function delete(Notification $notification): bool;
    public function findByUserId(int $userId): array;
    public function findByUserIdAndType(int $userId, int $typeId): array;
}
