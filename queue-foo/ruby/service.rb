require 'torquebox-messaging'

class Service
  def start
    TorqueBox::Messaging::Queue.new("/queue/foo").publish("start", :encoding => :edn)
  end
end
