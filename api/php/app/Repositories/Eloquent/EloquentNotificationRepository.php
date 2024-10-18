<?php

namespace App\Repositories\Eloquent;

use App\Models\Notification;
use App\Repositories\NotificationRepository;

class EloquentNotificationRepository implements NotificationRepository
{
    public function create(array $data): Notification
    {
        return Notification::create($data);
    }

    public function findById(int $id): ?Notification
    {
        return Notification::find($id);
    }

    public function update(Notification $notification, array $data): bool
    {
        return $notification->update($data);
    }

    public function delete(Notification $notification): bool
    {
        return $notification->delete();
    }

    public function findByUserId(int $userId): array
    {
        return Notification::where('user_id', $userId)->get()->toArray();
    }

    public function findByUserIdAndType(int $userId, int $typeId): array
    {
        return Notification::where('user_id', $userId)->where('notification_type_id', $typeId)->get()->toArray();
    }
}
