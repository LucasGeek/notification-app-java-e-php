<?php

namespace App\Repositories\Eloquent;

use App\Models\NotificationType;
use App\Repositories\NotificationTypeRepository;

class EloquentNotificationTypeRepository implements NotificationTypeRepository
{
    public function create(array $data): NotificationType
    {
        return NotificationType::create($data);
    }

    public function findById(int $id): ?NotificationType
    {
        return NotificationType::find($id);
    }

    public function update(NotificationType $notificationType, array $data): bool
    {
        return $notificationType->update($data);
    }

    public function delete(NotificationType $notificationType): bool
    {
        return $notificationType->delete();
    }

    public function findByUserId(int $userId): array
    {
        return NotificationType::where('user_id', $userId)->get()->toArray();
    }
}
