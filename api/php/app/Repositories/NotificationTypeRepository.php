<?php

namespace App\Repositories;

use App\Models\NotificationType;

interface NotificationTypeRepository
{
    public function create(array $data): NotificationType;
    public function findById(int $id): ?NotificationType;
    public function update(NotificationType $notificationType, array $data): bool;
    public function delete(NotificationType $notificationType): bool;
    public function findByUserId(int $userId): array;
}
